package com.pavcore.task.management.system.controller;

import com.pavcore.task.management.system.dto.request.TaskRequestTO;
import com.pavcore.task.management.system.dto.response.TaskResponseTO;
import com.pavcore.task.management.system.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Получить задачу по ID",
            description = "Возвращает задачу по ее уникальному идентификатору")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseTO> getTask(@DecimalMin(value = "1") @NotNull @PathVariable long id) {
        TaskResponseTO taskResponseTO = taskService.getTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Получить список задач по ID автора",
            description = "Возвращает список задач по уникальному идентификатору автора")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список задач"),
            @ApiResponse(responseCode = "404", description = "Список задач не найден"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/author")
    public ResponseEntity<List<TaskResponseTO>> getTasksByAuthor(@DecimalMin(value = "1") @NotNull @RequestParam long authorId,
                                                                 @DecimalMin(value = "0") @NotNull @RequestParam(defaultValue = "0") int page,
                                                                 @DecimalMin(value = "0") @NotNull @RequestParam(defaultValue = "10") int size) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByAuthor(authorId, page, size);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Operation(summary = "Получить список задач по ID исполнителя",
            description = "Возвращает список задач по уникальному идентификатору исполнителя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список задач"),
            @ApiResponse(responseCode = "404", description = "Список задач не найден"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/performer")
    public ResponseEntity<List<TaskResponseTO>> getTasksByPerformer(@DecimalMin(value = "1") @NotNull @RequestParam long performerId,
                                                                    @DecimalMin(value = "0") @NotNull @RequestParam(defaultValue = "0") int page,
                                                                    @DecimalMin(value = "0") @NotNull @RequestParam(defaultValue = "10") int size) {
        List<TaskResponseTO> taskResponseTOS = taskService.getTasksByPerformer(performerId, page, size);
        return ResponseEntity.ok(taskResponseTOS);
    }

    @Operation(summary = "Создает задачу на основе переданных данных",
            description = "Возвращает созданную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получена задача"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @PostMapping()
    public ResponseEntity<TaskResponseTO> createTask(@Valid @RequestBody TaskRequestTO taskRequestTO) {
        TaskResponseTO taskResponseTO = taskService.createTask(taskRequestTO);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Обновляет задачу на основе переданных данных",
            description = "Возвращает обновленную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получена обновленная задача"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @PutMapping
    public ResponseEntity<TaskResponseTO> updateTask(@Valid @RequestBody TaskRequestTO taskRequestTO,
                                                     @DecimalMin(value = "1") @NotNull @RequestParam long id) {
        TaskResponseTO taskResponseTO = taskService.updateTask(taskRequestTO, id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Удаляет задачу по ID",
            description = "Возвращает true если задача удалена, false если не удалена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<Boolean> deleteTask(@DecimalMin(value = "1") @NotNull @RequestParam long id) {
        boolean taskResponseTO = taskService.deleteTask(id);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Обновляет статус задачи",
            description = "Возвращает обновленную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус обновлен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @PutMapping("/status")
    public ResponseEntity<TaskResponseTO> updateTaskStatus(@NotNull @NotBlank @Size(min = 4, max = 32, message = "Статус должен быть от 4 до 32 символов") @RequestParam String status,
                                                           @DecimalMin(value = "1") @NotNull @RequestParam long taskId,
                                                           HttpServletRequest request) {
        TaskResponseTO taskResponseTO = taskService.changeStatus(status, taskId, request);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Обновляет приоритет задачи",
            description = "Возвращает обновленную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Приорите обновлен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/priority")
    public ResponseEntity<TaskResponseTO> updateTaskPriority(@NotNull @NotBlank @Size(min = 4, max = 32, message = "Приоритет должен быть от 4 до 32 символов") @RequestParam String priority,
                                                             @DecimalMin(value = "1") @NotNull @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePriority(priority, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Обновляет исполнителя задачи",
            description = "Возвращает обновленную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Исполнитель обновлен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @Secured({"ROLE_ADMIN"})
    @PutMapping("/performer")
    public ResponseEntity<TaskResponseTO> updateTaskPerformer(@DecimalMin(value = "1") @NotNull @RequestParam long performerId,
                                                              @DecimalMin(value = "1") @NotNull @RequestParam long taskId) {
        TaskResponseTO taskResponseTO = taskService.changePerformer(performerId, taskId);
        return ResponseEntity.ok(taskResponseTO);
    }

    @Operation(summary = "Обновляет комментарий задачи",
            description = "Возвращает обновленную задачу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий обновлен"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не авторизован"),
            @ApiResponse(responseCode = "403", description = "У пользователя не достаточно прав")
    })
    @PutMapping("/comment")
    public ResponseEntity<TaskResponseTO> updateTaskComment(@RequestParam String comment,
                                                            @DecimalMin(value = "1") @NotNull @RequestParam long taskId,
                                                            HttpServletRequest request) {
        TaskResponseTO taskResponseTO = taskService.changeComment(comment, taskId, request);
        return ResponseEntity.ok(taskResponseTO);
    }
}
