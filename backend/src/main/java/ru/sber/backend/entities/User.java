package ru.sber.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.backend.entities.enums.EGender;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность клиента ресторана
 */
@Data
@Table(name = "clients")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String login;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String username;

    @NotBlank
    @Size(max = 100)
    private int number;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private EGender gender;

    @NotBlank
    @Size(max = 100)
    private Date dateOfBirth;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(long id) {
        this.id = id;
    }
}
