package ru.sber.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sber.backend.entities.OrderToken;
import ru.sber.backend.repositories.OrderTokenRepository;

import java.util.List;

/**
 * Реализует логику для работы с токеном клиента сервиса заказа
 */
@Slf4j
@Service
public class OrderTokenServiceImpl implements OrderTokenService {

    private final OrderTokenRepository orderTokenRepository;

    @Autowired
    public OrderTokenServiceImpl(OrderTokenRepository orderTokenRepository) {
        this.orderTokenRepository = orderTokenRepository;
    }
    
    @Override
    public boolean save(OrderToken orderToken) {
        log.info("Добавление в базу данных токена {}", orderToken.getAccessToken());
        orderTokenRepository.save(orderToken);
        return true;
    }
    @Override
    public List<OrderToken> findAll() {
        log.info("Получение токена(ов) из базы данных");
        return orderTokenRepository.findAll();
    }
}
