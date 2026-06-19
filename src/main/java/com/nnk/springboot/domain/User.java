package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Entité représentant un utilisateur de l'application.
 *
 * <p>Le mot de passe doit respecter une politique de complexité : 8 caractères minimum,
 * une majuscule, un chiffre et un caractère spécial. Il est stocké encodé en BCrypt
 * (voir {@link com.nnk.springboot.services.UserService}).</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /** Identifiant de connexion. Obligatoire et unique. */
    @NotBlank(message = "Username is mandatory")
    private String username;

    /**
     * Mot de passe de l'utilisateur. Obligatoire.
     * Doit contenir au moins 8 caractères, une majuscule, un chiffre et un symbole.
     * Stocké encodé en BCrypt, jamais en clair.
     */
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$",
            message = "Password must be at least 8 characters and contain one uppercase letter, one digit and one symbol")
    private String password;

    /** Nom complet affiché. Obligatoire. */
    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    /** Rôle Spring Security (ex. {@code USER}, {@code ADMIN}). Obligatoire. */
    @NotBlank(message = "Role is mandatory")
    private String role;
}