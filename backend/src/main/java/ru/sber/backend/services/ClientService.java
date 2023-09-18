package ru.sber.backend.services;

import ru.sber.backend.entities.Client;

import java.util.Optional;

public interface ClientService {
    /**
     * Регистрирует пользователя
     *
     * @param client Данные о пользователе
     * @return Возвращает идентификатор созданного пользователя
     */
    long signUp(Client client);

    /**
     * Производит поиск пользователя по id
     *
     * @param clientId Уникальный идентификатор пользователя
     * @return Возвращает найденного пользователя
     */
    Optional<Client> getClientById(long clientId);

    /**
     * Проверяет существует ли пользователь
     *
     * @param clientId Уникальный идентификатор пользователя
     * @return Возвращает результат проверки
     */
    boolean checkClientExistence(long clientId);

    /**
     * Удаляет пользователя по id
     *
     * @param clientId Уникальный идентификатор пользователя
     * @return Возвращает true при удачном удалении и false, если пользователя не существует
     */
    boolean deleteClientById(long clientId);

    Optional<Client> findByLoginAndPassword(String login, String password);
}
