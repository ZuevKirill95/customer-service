package ru.sber.backend.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.sber.backend.entities.Notify;
import ru.sber.backend.models.OrderNotify;
import ru.sber.backend.models.OrderResponse;
import ru.sber.backend.services.NotifyService;

/**
 * Реализует слушаетля в kafka
 */
@Slf4j
@Component
public class OrderKafkaListener {
    private final NotifyService notifyService;

    @Autowired
    public OrderKafkaListener(NotifyService notifyService) {
        this.notifyService = notifyService;
    }

    /**
     * Создает уведомление
     */
    @KafkaListener(topics = "client_status", groupId = "updateClientOrder")
    void courierOrderListener(ConsumerRecord<String, Object> record) throws JsonProcessingException {
        log.info("Создает уведомление о смене статуса заказа {}", record);
        String value = record.value().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        OrderNotify orderNotify = objectMapper.readValue(value, OrderNotify.class);
        log.info("Получили: {}", orderNotify);
        notifyService.create(new Notify(orderNotify.getClientId(), orderNotify.getId()));
    }
}
