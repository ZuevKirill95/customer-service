package ru.sber.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.enums.EGender;

import java.util.Date;

/**
 * Сущность клиента ресторана
 */
@Data
@Table(name = "clients")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String login;

    private String name;

    private int number;

    private EGender gender;

    private Date dateOfBirth;

    private String email;

    private String password;
}
