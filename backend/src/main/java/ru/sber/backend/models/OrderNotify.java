package ru.sber.backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Уведомление о заказе
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderNotify {
    private Long id;
    private String clientId;
    private String clientName;
    private String description;
    private String clientPhone;
    private String status;
    private LocalDateTime orderTime;
    private LocalDateTime orderCookingTime;
    private LocalDateTime orderCookedTime;
    private String address;
    private String branchAddress;
    private Long branchId;
    private Long employeeRestaurantId;
    private List<?> dishesOrders;
}
