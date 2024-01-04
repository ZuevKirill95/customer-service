package ru.sber.backend.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Блюдо
 */
@NoArgsConstructor
@Data
public class Dish {

    private Long id;

    private String name;

    private String description;

    private String urlImage;

    private Category category;

    private BigDecimal price;

    private double weight;

}
