package ru.sber.backend.services;

import org.springframework.stereotype.Service;
import ru.sber.backend.entities.Dish;

import java.util.List;

/**
 * Сервис для взаимодействия с корзиной пользователя
 */
@Service
public class CartServiceImpl implements CartService {
    @Override
    public boolean addToCart(long clientId, long dishId) {
        return false;
    }

    @Override
    public boolean deleteDish(long clientId, long dishId) {
        return false;
    }

    @Override
    public void clearCart(long clientId) {

    }

    @Override
    public List<Dish> getListOfDishesInCart(long clientId) {
        return null;
    }

    @Override
    public int countDishesInCart(long clientId) {
        return 0;
    }
}
