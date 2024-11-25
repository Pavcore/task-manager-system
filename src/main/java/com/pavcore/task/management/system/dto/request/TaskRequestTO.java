package com.pavcore.task.management.system.dto.request;

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
public class TaskRequestTO {

    @NotNull(message = "Название не должно быть null")
    @Size(min = 4, max = 64, message = "Название должно быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    private String title;

    @Size(max = 512, message = "Описание не может быть больше 512 символов")
    private String description;

    @NotNull(message = "Статус не должен быть null")
    @Size(min = 4, max = 64, message = "Статус должен быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    private String status;

    @NotNull(message = "Приоритет не должен быть null")
    @Size(min = 4, max = 64, message = "Приоритет должен быть от 4 до 32 символов")
    @NotBlank(message = "Строку необходимо заполнить")
    private String priority;

    @Size(max = 512, message = "Комментарий не может быть больше 512 символов")
    private String comment;

    @NotNull(message = "Автор не должен быть null")
    @NotBlank(message = "Строку необходимо заполнить")
    @DecimalMin(value = "1", message = "Строка должна содержать числа больше нуля")
    private long author;

    @NotNull(message = "Исполнитель не должен быть null")
    @NotBlank(message = "Строку необходимо заполнить")
    @DecimalMin(value = "1", message = "Строка должна содержать числа больше нуля")
    private long performer;
}
