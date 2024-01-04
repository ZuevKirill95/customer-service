package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Хранит данные о пользователе требуемы для отправки в keycloak
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String email;
    private boolean enabled;
    private List<Credential> credentials;
    private Attributes attributes;
}