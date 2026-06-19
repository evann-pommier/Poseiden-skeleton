package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Entité représentant une notation financière agrégée issue de trois agences de rating.
 *
 * <p>Chaque {@code Rating} regroupe les notations de Moody's, S&amp;P et Fitch pour un même
 * instrument ou émetteur. Le champ {@code orderNumber} détermine l'ordre d'affichage
 * dans les listes.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /** Notation attribuée par Moody's. Obligatoire. */
    @NotBlank(message = "Moodys Rating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;

    /** Notation attribuée par S&P. Obligatoire. */
    @NotBlank(message = "SandP Rating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;

    /** Notation attribuée par Fitch. Obligatoire. */
    @NotBlank(message = "Fitch Rating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;

    /** Numéro d'ordre d'affichage. Obligatoire et strictement positif. */
    @NotNull(message = "Order Number is mandatory")
    @Positive(message = "Order Number must be a positive number")
    @Column(name = "orderNumber")
    private Integer orderNumber;

    /**
     * Crée un {@code Rating} avec les notations des trois agences et un numéro d'ordre.
     *
     * @param moodysRating notation Moody's
     * @param sandPRating  notation S&P
     * @param fitchRating  notation Fitch
     * @param orderNumber  numéro d'ordre d'affichage (strictement positif)
     */
    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}