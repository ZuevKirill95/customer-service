package ru.sber.backend.services;

import ru.sber.backend.entities.Dish;

import java.util.List;

public interface CartService {
    /**
     * Добавление блюда в корзину
     *
     * @param clientId    Уникальный идентификатор пользователя
     * @param dishId Уникальный идентификатор блюда
     * @return Возвращает статус добавления блюда в корзину
     */
    boolean addToCart(long clientId, long dishId);

    /**
     * Удаление товара из корзины
     *
     * @param clientId    Уникальный идентификатор пользователя
     * @param dishId Уникальный идентификатор блюда
     * @return Возвращает статус удаления блюда из корзины
     */
    boolean deleteDish(long clientId, long dishId);

    /**
     * Полностью очищает корзину пользователя
     *
     * @param clientId Уникальный идентификатор пользователя
     */
    void clearCart(long clientId);

    /**
     * Выдает список блюд в корзине пользователя
     *
     * @param clientId Уникальный идентификатор пользователя
     * @return Возвращает список блюд
     */
    List<Dish> getListOfDishesInCart(long clientId);

    /**
     * Подсчитывает количество блюд в корзине пользователя
     *
     * @param clientId Уникальный идентификатор пользователя
     * @return Возвращает количество товаров
     */
    int countDishesInCart(long clientId);
}
