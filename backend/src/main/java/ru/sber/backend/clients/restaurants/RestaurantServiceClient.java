package ru.sber.backend.clients.restaurants;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sber.backend.models.Dish;
import ru.sber.backend.models.DishFromCart;
import java.util.List;


/**
 * Интерфейс для взаимодействия с сервисом ресторана
 */
@FeignClient(value = "feignDishes", url = "http://localhost:8082/dishes")
public interface RestaurantServiceClient {
    /**
     * Получает необходимую страницу блюд с запрашиваемым размером
     *
     * @return получение страницы блюд
     */
    @GetMapping(value = "/customer/any", produces = "application/json")
    Page<Dish> getListAllDish(@RequestParam int page, @RequestParam int size);

    /**
     * Возвращает список блюд по их id
     * @param list id блюд
     * @return список блюд
     */
    @GetMapping(value = "/customer/basket", produces = "application/json")
    List<DishFromCart> getListDishesById(@RequestParam String list);
}
