package ru.sber.backend.clients.restaurants;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ru.sber.backend.models.Dish;
import ru.sber.backend.models.DishFromCart;

import java.util.List;

/**
 * Реализация бизнес логики взаимодействия с сервисом ресторана
 */
@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantServiceClient restaurantServiceClient;

    @Autowired
    public RestaurantServiceImpl(RestaurantServiceClient restaurantServiceClient) {
        this.restaurantServiceClient = restaurantServiceClient;
    }

    @Override
    public Page<Dish> getListAllDish(int page, int size) {
        log.info("Возвращает список всех блюд");

        return restaurantServiceClient
                .getListAllDish(page, size);
    }

    @Override
    public List<DishFromCart> getListDishesById(String list) {
        log.info("Возвращает список блюд по их id");
        return restaurantServiceClient
                .getListDishesById(list);
    }


}
