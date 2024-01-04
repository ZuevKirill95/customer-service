package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Атрибуты в keycloak пользователя
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Attributes {
    private String phoneNumber;
    private String dateBirthday;
}