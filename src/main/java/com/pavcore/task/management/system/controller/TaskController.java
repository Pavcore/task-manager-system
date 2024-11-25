package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Secured({"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseTO> getTask(@DecimalMin(value = "1") @NotNull @PathVariable long id) {
        TaskResponseTO taskResponseTO = taskService.getTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @GetMapping("/author")
    public ResponseEntity<List<TaskResponseTO>> getTasksByAuthor(@DecimalMin(value = "1") @NotNull @RequestParam long authorId) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByAuthor(authorId);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Secured({"ADMIN"})
    @GetMapping("/performer")
    public ResponseEntity<List<TaskResponseTO>> getTasksByPerformer(@DecimalMin(value = "1") @NotNull @RequestParam long performerId) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByPerformer(performerId);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<TaskResponseTO> createTask(@Valid @RequestBody TaskRequestTO taskRequestTO) {
        TaskResponseTO taskResponseTO = taskService.createTask(taskRequestTO);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping
    public ResponseEntity<TaskResponseTO> updateTask(@Valid @RequestBody TaskRequestTO taskRequestTO,
                                                     @DecimalMin(value = "1") @NotNull @RequestParam long id) {
        TaskResponseTO taskResponseTO = taskService.updateTask(taskRequestTO, id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTask(@DecimalMin(value = "1") @NotNull @RequestParam long id) {
        boolean taskResponseTO = taskService.deleteTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @PutMapping("/status")
    public ResponseEntity<TaskResponseTO> updateTaskStatus(@NotNull @NotBlank @Size(min = 4, max = 32, message = "Статус должен быть от 4 до 32 символов") @RequestParam String status,
                                                           @DecimalMin(value = "1") @NotNull @RequestParam long taskId,
                                                           HttpServletRequest request) {
        TaskResponseTO taskResponseTO = taskService.changeStatus(status, taskId, request);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping("/priority")
    public ResponseEntity<TaskResponseTO> updateTaskPriority(@NotNull @NotBlank @Size(min = 4, max = 32, message = "Приоритет должен быть от 4 до 32 символов") @RequestParam String priority,
                                                             @DecimalMin(value = "1") @NotNull @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePriority(priority, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Secured({"ADMIN"})
    @PutMapping("/performer")
    public ResponseEntity<TaskResponseTO> updateTaskPerformer(@DecimalMin(value = "1") @NotNull @RequestParam long performerId,
                                                              @DecimalMin(value = "1") @NotNull @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePerformer(performerId, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @PutMapping("/comment")
    public ResponseEntity<TaskResponseTO> updateTaskComment(@RequestParam String comment,
                                                            @DecimalMin(value = "1") @NotNull @RequestParam long taskId,
                                                            HttpServletRequest request) {
        TaskResponseTO taskResponseTO = taskService.changeComment(comment, taskId, request);
        return ResponseEntity.ok(taskResponseTO);
    }
}
