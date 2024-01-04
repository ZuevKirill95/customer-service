package ru.sber.backend.entities.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные для авторизации
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class LoginRequest {

    private String username;
    private String password;
}
