package com.pavcore.task.management.system.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "модель данных задачи, отправляемая в ответе")
public class TaskResponseTO {

    @Schema(description = "Уникальный идентификатор задачи", example = "1")
    private long id;

    @Schema(description = "Название задачи", example = "Сделать код ревью")
    private String title;

    @Schema(description = "Описание задачи",
            example = "В понедельник, провести код ревью у новых сотрудников")
    private String description;

    @Schema(description = "Статус задачи", example = "в процессе")
    private String status;

    @Schema(description = "Приоритет задачи", example = "высокий")
    private String priority;

    @Schema(description = "Список комментариев данной задачи",
            example = "в понедельник не успею, сделаю во вторник",
            examples = "во вторник тоже не успею, переношу на следующую неделю")
    private List<CommentResponseTo> comments;

    @Schema(description = "Уникальный идентификатор автора", example = "1")
    private long author;

    @Schema(description = "Уникальный идентификатор исполнителя", example = "2")
    private long performer;
}
