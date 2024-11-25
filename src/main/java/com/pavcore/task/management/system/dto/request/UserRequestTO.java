package com.pavcore.task.management.system.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Модель данных пользователя, получаемая из запроса")
public class UserRequestTO {

    @NotNull(message = "Email не должен быть null")
    @Size(min = 12, max = 64, message = "Email должен быть от 12 до 64 символов")
    @NotBlank(message = "Строка не может быть пустой")
    @Email(message = "Некорректный формат email")
    @Schema(description = "Адрес электронной почты пользователя", example = "pavcore@examle.com")
    private String email;

    @NotNull(message = "Пароль не должен быть null")
    @Size(min = 8, max = 64, message = "Пароль должен быть от 8 до 64 символов")
    @NotBlank(message = "Пароль не может быть пустой")
    @Schema(description = "Пароль пользователя", example = "123qwe654Jdsa!")
    private String password;

}
