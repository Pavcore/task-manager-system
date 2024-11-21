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

    private final TaskRepo taskRepo;
    private final ResponseMapper responseMapper;
    private final RequestMapper requestMapper;

    @Autowired
    public TaskService(TaskRepo taskRepo, ResponseMapper responseMapper, RequestMapper requestMapper) {
        this.taskRepo = taskRepo;
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
        } else return false;
    }

    public boolean changeStatus(String status, long taskId, User user) {
        if (checkTaskInDbAndOwnerThisTask(taskId, user)) {
            taskRepo.findById(taskId).get().setStatus(status);
            return true;
        }
        return false;
    }

    public boolean changePriority(String priority, long taskId, User user) {
        if (checkTaskInDbAndOwnerThisTask(taskId, user)) {
            taskRepo.findById(taskId).get().setPriority(priority);
            return true;
        }
        return false;
    }

    public boolean changePerformer(User performer, long taskId, User user) {
        if (checkTaskInDbAndOwnerThisTask(taskId, user)) {
            taskRepo.findById(taskId).get().setPerformer(performer);
            return true;
        }
        return false;
    }

    public boolean changeComment(Comment comment, long taskId, User user) {
        if (checkTaskInDbAndOwnerThisTask(taskId, user)) {
            taskRepo.findById(taskId).get().getComment().add(comment);
            return true;
        }
        return false;
    }

    public List<TaskResponseTO> getTasksByAuthor(User author) {
        List<TaskResponseTO> taskResponseTOS = new ArrayList<>();
        for (Task task : taskRepo.getTasksByAuthor(author)) {
            taskResponseTOS.add(responseMapper.map(task));
        }
        return taskResponseTOS;
    }

    private boolean checkTaskInDbAndOwnerThisTask(long taskId, User user) {
        if (taskRepo.findById(taskId).isPresent()) {
            Task task = taskRepo.findById(taskId).get();
            return user.getRole() == Role.ADMIN
                    || task.getPerformer().getId() == user.getId();
        }
        return false;
    }
}
