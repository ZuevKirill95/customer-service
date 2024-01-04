package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Токен для сброса
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RefreshToken {
    private String refresh_token;
}
