package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Данные для запроса по сбросу пароля в keycloak
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    private String type;
    private Boolean temporary;
    private String value;
}
