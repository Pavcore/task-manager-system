package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.TaskRepo;
import com.pavcore.task.management.system.dto.Role;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.exception.NoAccessException;
import com.pavcore.task.management.system.mapper.RequestMapper;
import com.pavcore.task.management.system.mapper.ResponseMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
        task.setAuthor(userService.getUserById(taskRequestTO.getAuthor()));
        task.setPerformer(userService.getUserById(taskRequestTO.getPerformer()));
        taskRepo.save(task);
        if (!taskRequestTO.getComment().isEmpty()) {
            Comment comment = Comment.builder()
                    .content(taskRequestTO.getComment())
                    .task(task)
                    .build();
            commentService.save(comment);
        }
        return responseMapper.map(task);
    }

    @Transactional
    public TaskResponseTO updateTask(TaskRequestTO taskRequestTO, long taskId) {
        Task taskFromDb = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        Task task = requestMapper.map(taskRequestTO);
        task.setId(taskFromDb.getId());
        taskRepo.save(task);
        return responseMapper.map(task);
    }

    public TaskResponseTO getTask(long taskId) {
        Task task = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        return responseMapper.map(task);
    }

    @Transactional
    public boolean deleteTask(long taskId) {
        taskRepo.deleteById(taskId);
        return true;
    }

    @Transactional
    public TaskResponseTO changeStatus(String status, long taskId, HttpServletRequest request) {
        Task taskFromDb = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        User performer = userService.getUserByToken(request);
        if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
            taskFromDb.setStatus(status);
            taskRepo.save(taskFromDb);
        } else throw new NoAccessException("No access");
        return responseMapper.map(taskFromDb);
    }

    @Transactional
    public TaskResponseTO changePriority(String priority, long taskId) {
        Task taskFromDb = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        taskFromDb.setPriority(priority);
        taskRepo.save(taskFromDb);
        return responseMapper.map(taskFromDb);
    }

    @Transactional
    public TaskResponseTO changePerformer(long performerId, long taskId) {
        User newPerformer = userService.getUserById(performerId);
        Task taskFromDb = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        taskFromDb.setPerformer(newPerformer);
        taskRepo.save(taskFromDb);
        return responseMapper.map(taskFromDb);
    }

    @Transactional
    public TaskResponseTO changeComment(String content, long taskId, HttpServletRequest request) {
        Task taskFromDb = taskRepo.findById(taskId)
                .orElseThrow(EntityNotFoundException::new);
        User performer = userService.getUserByToken(request);
        Comment comment = Comment.builder()
                .task(taskFromDb)
                .content(content)
                .build();
        if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
            commentService.save(comment);
        } else throw new NoAccessException("No access");
        return responseMapper.map(taskFromDb);
    }

    public List<TaskResponseTO> getTasksByAuthor(long authorId, int offset, int limit) {
        User author = userService.getUserById(authorId);
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByAuthor(author, PageRequest.of(offset, limit))) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }

    public List<TaskResponseTO> getTasksByPerformer(long performerId, int offset, int limit) {
        User performer = userService.getUserById(performerId);
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByPerformer(performer, PageRequest.of(offset, limit))) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }
}
