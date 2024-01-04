package ru.sber.backend.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sber.backend.models.DishOrder;
import ru.sber.backend.models.OrderResponse;
import ru.sber.backend.models.ResetPassword;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Slf4j
@Service
@Data
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    private final JwtService jwtService;
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, JwtService jwtService) {
        this.mailSender = mailSender;
        this.jwtService = jwtService;
    }

    @Override
    public void sendOrderConfirmation(OrderResponse order) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG);
            helper.setFrom(this.email);

            String email = jwtService.getEmailClaim(jwtService.getJwtSecurityContext());
            helper.setTo(email);

            helper.setSubject("Онлайн чек заказа");
            StringBuilder emailText = new StringBuilder(String.format("Уважаемый %s!\n\n", order.getClientName()));
            emailText.append(String.format("Дата и время оформления заказа: %s\n\n", LocalDateTime.now().format(dateTimeFormatter)));

            for (DishOrder dishOrder : order.getListDishesFromOrder()) {
                emailText.append(String.format("%s - %d шт.\n", dishOrder.getDishName(), dishOrder.getQuantity()));
            }

            emailText.append(String.format("\nСтоимость заказа: %s руб.\n\n", order.getTotalPrice()));
            emailText.append("Спасибо за ваш заказ!");
            helper.setText(emailText.toString());

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendResetPassword(ResetPassword resetPassword) throws MessagingException {
        log.info("send message: {}", resetPassword);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(this.email);

        helper.setTo(resetPassword.getEmail());
        helper.setSubject("Восстановление пароля");
        StringBuilder emailText = new StringBuilder(String.format("username: %s\n", resetPassword.getUsername()));
        emailText.append(String.format("Новый пароль от аккаунта: %s", resetPassword.getPassword()));
        helper.setText(emailText.toString());
        mailSender.send(message);

    }


}
