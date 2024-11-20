package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        return ResponseEntity.ok(new Task());
    }

    @Secured({"ADMIN"})
    @GetMapping("/author")
    public ResponseEntity<List<Task>> getTasksByAuthor(@RequestParam long authorId) {
        return ResponseEntity.ok(new ArrayList<Task>());
    }

    @Secured({"ADMIN"})
    @GetMapping("/performer")
    public ResponseEntity<List<Task>> getTasksByPerformer(@RequestParam long performerId) {
        return ResponseEntity.ok(new ArrayList<Task>());
    }

    @Secured({"ADMIN"})
    @PostMapping()
    public ResponseEntity<Task> createTask(@RequestBody TaskRequestTO taskRequestTO) {
        return ResponseEntity.ok(new Task());
    }

    @Secured({"ADMIN"})
    @PutMapping
    public ResponseEntity<Task> updateTask(@RequestBody TaskRequestTO taskRequestTO) {
        return ResponseEntity.ok(new Task());
    }

    @Secured({"ADMIN"})
    @DeleteMapping
    public ResponseEntity<Task> deleteTask(@RequestBody TaskRequestTO taskRequestTO) {
        return ResponseEntity.ok(new Task());
    }

    @PutMapping("/status")
    public ResponseEntity<Task> updateTaskStatus(@RequestParam String status) {
        return ResponseEntity.ok(new Task());
    }

    @Secured({"ADMIN"})
    @PutMapping("/priority")
    public ResponseEntity<Task> updateTaskPriority(@RequestParam String priority) {
        return ResponseEntity.ok(new Task());
    }

    @Secured({"ADMIN"})
    @PutMapping("/performer")
    public ResponseEntity<Task> updateTaskPerformer(@RequestParam String performer) {
        return ResponseEntity.ok(new Task());
    }

    @PutMapping("/comment")
    public ResponseEntity<Task> updateTaskComment(@RequestParam String comment) {
        return ResponseEntity.ok(new Task());
    }
}
