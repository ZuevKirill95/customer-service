package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Учетные данные для конфигурации keycloak
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Credential {
    private String type;
    private String value;

}