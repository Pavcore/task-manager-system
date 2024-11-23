package com.pavcore.task.management.system.service;

import com.pavcore.task.management.system.dao.entity.Comment;
import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dao.entity.User;
import com.pavcore.task.management.system.dao.repo.TaskRepo;
import com.pavcore.task.management.system.dto.Role;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.mapper.RequestMapper;
import com.pavcore.task.management.system.mapper.ResponseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TaskResponseTO createTask(TaskRequestTO taskRequestTO) {
        Task task = requestMapper.map(taskRequestTO);
        taskRepo.save(task);
        return responseMapper.map(task);
    }

    public TaskResponseTO updateTask(TaskRequestTO taskRequestTO, long taskId) {
        Task taskFromDb = taskRepo.getReferenceById(taskId);
        Task task = requestMapper.map(taskRequestTO);
        task.setId(taskFromDb.getId());
        taskRepo.save(task);
        return responseMapper.map(task);
    }

    public TaskResponseTO getTask(long taskId) {
        if (taskRepo.findById(taskId).isEmpty()) {
            return null;
        }
        Task task = taskRepo.findById(taskId).get();
        return responseMapper.map(task);
    }

    public boolean deleteTask(long taskId) {
        if (taskRepo.existsById(taskId)) {
            taskRepo.deleteById(taskId);
            return true;
        }
        return false;
    }

    public TaskResponseTO changeStatus(String status, long taskId, long userId) {
        Task taskFromDb = taskRepo.getReferenceById(taskId);
        User performer = userService.getUserById(userId);
        if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
            taskFromDb.setStatus(status);
            taskRepo.save(taskFromDb);
        }
        return responseMapper.map(taskFromDb);
    }

    public TaskResponseTO changePriority(String priority, long taskId) {
        Task taskFromDb = taskRepo.getReferenceById(taskId);
        taskFromDb.setPriority(priority);
        taskRepo.save(taskFromDb);
        return responseMapper.map(taskFromDb);
    }

    public TaskResponseTO changePerformer(long taskId, long userId) {
        Task taskFromDb = taskRepo.getReferenceById(taskId);
        User newPerformer = userService.getUserById(userId);
        taskFromDb.setPerformer(newPerformer);
        taskRepo.save(taskFromDb);
        return responseMapper.map(taskFromDb);
    }

    public TaskResponseTO changeComment(String content, long taskId, long userId) {
        Task taskFromDb = taskRepo.getReferenceById(taskId);
        User performer = userService.getUserById(userId);
        Comment comment = Comment.builder()
                .task(taskFromDb)
                .content(content)
                .build();
        if (taskFromDb.getPerformer().equals(performer) || performer.getRole().equals(Role.ADMIN)) {
            taskFromDb.getComments().add(comment);
            taskRepo.save(taskFromDb);
            commentService.save(comment);
        }
        return responseMapper.map(taskFromDb);
    }

    public List<TaskResponseTO> getTasksByAuthor(long authorId) {
        User author = userService.getUserById(authorId);
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByAuthor(author)) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }

    public List<TaskResponseTO> getTasksByPerformer(long performerId) {
        User performer = userService.getUserById(performerId);
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByAuthor(performer)) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }
}
