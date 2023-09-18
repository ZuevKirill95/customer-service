package ru.sber.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sber.backend.entities.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientByLoginAndPassword(String login, String password);

    Optional<Client> findByName(String username);

    Boolean existsByName(String username);

    Boolean existsByEmail(String email);
}
