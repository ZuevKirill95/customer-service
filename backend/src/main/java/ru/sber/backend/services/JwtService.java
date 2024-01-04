package ru.sber.backend.services;

import org.springframework.security.oauth2.jwt.Jwt;

/**
 * Возвращает данные пользователя из токена
 */
public interface JwtService {
    /**
     * Возврашает id пользователя по токену
     * @param jwt - токен
     * @return id пользователя
     */
    String getSubClaim(Jwt jwt);

    /**
     * Возврашает email пользователя по токену
     * @param jwt - токен
     * @return email
     */
    String getEmailClaim(Jwt jwt);

    /**
     * Возврашает номер телефона пользователя по токену
     * @param jwt - токен
     * @return номер телефона
     */
    String getPhoneNumberClaim(Jwt jwt);
    /**
     * Возврашает логин пользователя по токену
     * @param jwt - токен
     * @return логин
     */
    String getPreferredUsernameClaim(Jwt jwt);
    /**
     * Возврашает день рождения пользователя по токену
     * @param jwt - токен
     * @return день рождения
     */
    String getDateBirthdayClaim(Jwt jwt);

    /**
     * Возвращает токен пользователя
     * @return токен
     */
    Jwt getJwtSecurityContext();

}
