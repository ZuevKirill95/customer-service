package ru.sber.backend.services;

import jakarta.mail.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import ru.sber.backend.models.OrderResponse;
import ru.sber.backend.models.ResetPassword;

/**
 * Сервис отправки письма на электронную почту
 */
public interface EmailService {

    /**
     * Отправляет электронный чек на почту
     *
     * @param order созданный заказ
     */
    void sendOrderConfirmation(OrderResponse order);

    /**
     * Отправляет на почту сообщение с новым паролем
     * @param resetPassword новый пароль пользователя
     */
    void sendResetPassword(ResetPassword resetPassword) throws MessagingException;
}
