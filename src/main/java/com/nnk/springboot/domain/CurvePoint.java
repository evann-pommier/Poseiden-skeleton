package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Entité représentant un point sur une courbe de taux d'intérêt.
 *
 * <p>Un point est défini par l'identifiant de la courbe ({@code curveId}),
 * une échéance ({@code term}) exprimée en années et la valeur de taux
 * correspondante ({@code value}). L'ensemble des points d'une même courbe
 * permet de reconstituer sa structure par terme.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CurvePoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /** Identifiant de la courbe de taux à laquelle appartient ce point. Obligatoire. */
    @NotNull(message = "Curve Id is mandatory")
    private Integer curveId;

    /** Échéance du point sur la courbe, exprimée en années. Obligatoire. */
    @NotNull(message = "Term is mandatory")
    private Double term;

    /**
     * Valeur du taux pour cette échéance. Obligatoire.
     * Le nom de colonne est échappé ({@code `value`}) car {@code value}
     * est un mot réservé en SQL.
     */
    @NotNull(message = "Value is mandatory")
    @Column(name = "`value`")
    private Double value;

    /** Date et heure de création du point. */
    private LocalDateTime creationDate;

    /**
     * Crée un point de courbe avec ses trois champs obligatoires.
     *
     * @param curveId identifiant de la courbe de taux
     * @param term    échéance en années
     * @param value   valeur du taux correspondant
     */
    public CurvePoint(Integer curveId, Double term, Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}