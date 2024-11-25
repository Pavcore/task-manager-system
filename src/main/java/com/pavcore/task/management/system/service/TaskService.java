package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.TaskRepo;
import com.pavcore.task.management.system.dto.Role;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.exception.NoAccessException;
import com.pavcore.task.management.system.exception.NotFoundAuthorException;
import com.pavcore.task.management.system.exception.NotFoundTaskException;
import com.pavcore.task.management.system.mapper.RequestMapper;
import com.pavcore.task.management.system.mapper.ResponseMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final CommentService commentService;
    private final TaskRepo taskRepo;
    private final UserService userService;
    private final ResponseMapper responseMapper;
    private final RequestMapper requestMapper;

    @Autowired
    public TaskService(CommentService commentService, TaskRepo taskRepo, UserService userService, ResponseMapper responseMapper, RequestMapper requestMapper) {
        this.commentService = commentService;
        this.taskRepo = taskRepo;
        this.userService = userService;
        this.responseMapper = responseMapper;
        this.requestMapper = requestMapper;
    }

    @Transactional
    public TaskResponseTO createTask(TaskRequestTO taskRequestTO) {
        Task task = requestMapper.map(taskRequestTO);
        Comment comment = Comment.builder()
                .content(taskRequestTO.getComment())
                .task(task)
                .build();
        task.setAuthor(userService.getUserById(taskRequestTO.getAuthor()));
        task.setPerformer(userService.getUserById(taskRequestTO.getPerformer()));
        taskRepo.save(task);
        commentService.save(comment);
        return mapper(task);
    }

    @Transactional
    public TaskResponseTO updateTask(TaskRequestTO taskRequestTO, long taskId) {
        try {
            Task taskFromDb = taskRepo.getReferenceById(taskId);
            Task task = requestMapper.map(taskRequestTO);
            task.setId(taskFromDb.getId());
            taskRepo.save(task);
            return mapper(task);
        } catch (RuntimeException e) {
            throw new NotFoundTaskException(e.getMessage());
        }
    }

    public TaskResponseTO getTask(long taskId) {
        try {
            Task task = taskRepo.getReferenceById(taskId);
            return mapper(task);
        } catch (RuntimeException e) {
            throw new NotFoundTaskException(e.getMessage());
        }
    }

    @Transactional
    public boolean deleteTask(long taskId) {
        try {
            taskRepo.deleteById(taskId);
            return true;
        } catch (RuntimeException e) {
            throw new NotFoundTaskException(e.getMessage());
        }
    }

    @Transactional
    public TaskResponseTO changeStatus(String status, long taskId, HttpServletRequest request) {
        try {
            Task taskFromDb = taskRepo.getReferenceById(taskId);
            User performer = userService.getUserByToken(request);
            if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
                taskFromDb.setStatus(status);
                taskRepo.save(taskFromDb);
            } else throw new NoAccessException("No access");
            return mapper(taskFromDb);
        } catch (NoAccessException e) {
            throw new NoAccessException(e.getMessage());
        }
    }

    @Transactional
    public TaskResponseTO changePriority(String priority, long taskId) {
        try {
            Task taskFromDb = taskRepo.getReferenceById(taskId);
            taskFromDb.setPriority(priority);
            taskRepo.save(taskFromDb);
            return mapper(taskFromDb);
        } catch (RuntimeException e) {
            throw new NotFoundTaskException(e.getMessage());
        }
    }

    @Transactional
    public TaskResponseTO changePerformer(long performerId, long taskId) {
        try {
            User newPerformer = userService.getUserById(performerId);
            Task taskFromDb = taskRepo.getReferenceById(taskId);
            taskFromDb.setPerformer(newPerformer);
            taskRepo.save(taskFromDb);
            return mapper(taskFromDb);
        } catch (RuntimeException e) {
            throw new NotFoundTaskException(e.getMessage());
        }
    }

    @Transactional
    public TaskResponseTO changeComment(String content, long taskId, HttpServletRequest request) {
        try {
            Task taskFromDb = taskRepo.getReferenceById(taskId);
            User performer = userService.getUserByToken(request);
            Comment comment = Comment.builder()
                    .task(taskFromDb)
                    .content(content)
                    .build();
            if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
                commentService.save(comment);
            } else throw new NoAccessException("No access");
            return mapper(taskFromDb);
        } catch (NoAccessException e) {
            throw new NoAccessException(e.getMessage());
        }
    }

    public List<TaskResponseTO> getTasksByAuthor(long authorId) {
        try {
            User author = userService.getUserById(authorId);
            List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
            for (Task task : taskRepo.getTasksByAuthor(author)) {
                taskResponseTOS.add(responseMapper.map(task));
            }
            return taskResponseTOS;
        } catch (NotFoundAuthorException e) {
            throw new NotFoundAuthorException(e.getMessage());
        }
    }

    public List<TaskResponseTO> getTasksByPerformer(long performerId) {
        User performer = userService.getUserById(performerId);
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByPerformer(performer)) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }

    private TaskResponseTO mapper(Task task) {
        TaskResponseTO taskResponseTO = responseMapper.map(task);
        taskResponseTO.setAuthor(task.getAuthor().getId());
        taskResponseTO.setPerformer(task.getPerformer().getId());
        return taskResponseTO;
    }
}
