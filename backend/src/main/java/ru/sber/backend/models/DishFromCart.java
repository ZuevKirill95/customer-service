package ru.sber.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Блюда в корзине
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DishFromCart extends Dish {
    private int quantity;
}
