package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dao.entity.Task;
import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Secured({"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        return ResponseEntity.ok(new Task());
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
