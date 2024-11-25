package com.pavcore.task.management.system.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Модель данных комментария, отправляемая в ответе")
public class CommentResponseTo {

    @Schema(description = "Уникальный идентификатор комментария", example = "1")
    private long id;

    @Schema(description = "Оставляемый комментарий", example = "Доделаю в понедельник")
    private String content;

}
