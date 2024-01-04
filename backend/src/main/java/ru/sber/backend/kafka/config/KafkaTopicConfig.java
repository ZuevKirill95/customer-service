package ru.sber.backend.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Создает темы в kafka
 */
@Configuration
public class KafkaTopicConfig {
    /**
     * Создает тему для получения актуального статуса пользователя
     */
    @Bean
    public NewTopic clientStatus() {
        return TopicBuilder.name("client_status").build();
    }
}
