package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Secured({"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseTO> getTask(@PathVariable long id) {
        TaskResponseTO taskResponseTO = taskService.getTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @GetMapping("/author")
    public ResponseEntity<List<TaskResponseTO>> getTasksByAuthor(@RequestParam long authorId) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByAuthor(authorId);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Secured({"ADMIN"})
    @GetMapping("/performer")
    public ResponseEntity<List<TaskResponseTO>> getTasksByPerformer(@RequestParam long performerId) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByPerformer(performerId);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<TaskResponseTO> createTask(@RequestBody TaskRequestTO taskRequestTO) {
        TaskResponseTO taskResponseTO = taskService.createTask(taskRequestTO);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping
    public ResponseEntity<TaskResponseTO> updateTask(@RequestBody TaskRequestTO taskRequestTO, @RequestParam long id) {
        TaskResponseTO taskResponseTO = taskService.updateTask(taskRequestTO, id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTask(@RequestParam long id) {
        boolean taskResponseTO = taskService.deleteTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @PutMapping("/status")
    public ResponseEntity<TaskResponseTO> updateTaskStatus(@RequestParam String status,
                                                           @RequestParam long taskId,
                                                           @RequestParam long userId) {
        TaskResponseTO taskResponseTO = taskService.changeStatus(status, taskId, userId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping("/priority")
    public ResponseEntity<TaskResponseTO> updateTaskPriority(@RequestParam String priority,
                                                             @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePriority(priority, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping("/performer")
    public ResponseEntity<TaskResponseTO> updateTaskPerformer(@RequestParam long performerId,
                                                              @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePerformer(performerId, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @PutMapping("/comment")
    public ResponseEntity<TaskResponseTO> updateTaskComment(@RequestParam String comment,
                                                            @RequestParam long taskId,
                                                            @RequestParam long userId) {
        TaskResponseTO taskResponseTO = taskService.changeComment(comment, taskId, userId);
        return ResponseEntity.ok(taskResponseTO);
    }
}
