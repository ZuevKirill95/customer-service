package ru.sber.backend.models;

import lombok.Data;

/**
 * Хранит данные для обновления информации о пользователе
 */
@Data
public class UpdateResponse {
    String email;
    Attributes attributes;
}
