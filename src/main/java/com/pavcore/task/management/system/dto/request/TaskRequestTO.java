package com.pavcore.task.management.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "модель данных задачи, получаемая из запроса")
public class TaskRequestTO {

    @NotNull(message = "Название не должно быть null")
    @Size(min = 4, max = 64, message = "Название должно быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    @Schema(description = "Название задачи", example = "Сделать код ревью")
    private String title;

    @Size(max = 512, message = "Описание не может быть больше 512 символов")
    @Schema(description = "Описание задачи", example = "В понедельник, провести код ревью у новых сотрудников")
    private String description;

    @NotNull(message = "Статус не должен быть null")
    @Size(min = 4, max = 64, message = "Статус должен быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    @Schema(description = "Статус задачи", example = "в процессе")
    private String status;

    @NotNull(message = "Приоритет не должен быть null")
    @Size(min = 4, max = 64, message = "Приоритет должен быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    @Schema(description = "Приоритет задачи", example = "высокий")
    private String priority;

    @Size(max = 512, message = "Комментарий не может быть больше 512 символов")
    @Schema(description = "Комментарий задачи", example = "в понедельник не успею, сделаю во вторник")
    private String comment;

    @NotNull(message = "Автор не должен быть null")
    @DecimalMin(value = "1", message = "Строка должна содержать числа больше нуля")
    @Schema(description = "Уникальный идентификатор автора", example = "1")
    private long author;

    @NotNull(message = "Исполнитель не должен быть null")
    @DecimalMin(value = "1", message = "Строка должна содержать числа больше нуля")
    @Schema(description = "Уникальный идентификатор исполнителя", example = "2")
    private long performer;
}
