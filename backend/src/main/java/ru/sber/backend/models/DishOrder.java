package ru.sber.backend.models;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DishOrder {
    private Long orderId;

    private Long dishId;

    private String dishName;

    private Integer quantity;

}