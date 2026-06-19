package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

/**
 * Entité représentant une offre d'achat (bid) dans le système de trading.
 *
 * <p>Une offre est caractérisée par un compte ({@code account}), un type
 * d'instrument ({@code type}) et une quantité d'achat obligatoire
 * ({@code bidQuantity}). Les champs d'audit ({@code creationName},
 * {@code creationDate}, {@code revisionName}, {@code revisionDate})
 * tracent la création et les modifications de l'offre.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BidList")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;

    /** Compte associé à l'offre.*/
    @NotBlank(message = "Account is mandatory")
    private String account;

    /** Type d'instrument financier.*/
    @NotBlank(message = "Type is mandatory")
    private String type;

    /** Quantité proposée à l'achat.*/
    @NotNull
    @PositiveOrZero(message = "Bid Quantity must be numeric and positive or zero")
    @Column(name = "bidQuantity")
    private Double bidQuantity;

    /** Quantité proposée à la vente (ask).*/
    @PositiveOrZero(message = "Ask Quantity must be numeric and positive or zero")
    @Column(name = "askQuantity")
    private Double askQuantity;

    /** Prix d'achat proposé.*/
    @PositiveOrZero(message = "Bid must be numeric and positive or zero")
    private Double bid;

    /** Prix de vente proposé (ask).*/
    @PositiveOrZero(message = "Ask must be numeric and positive or zero")
    private Double ask;

    /** Indice de référence associé à l'offre. */
    private String benchmark;

    /** Date et heure de l'offre. */
    @Column(name = "bidListDate")
    private LocalDateTime bidListDate;

    /** Commentaire libre sur l'offre. */
    private String commentary;

    /** Titre ou instrument financier sous-jacent. */
    private String security;

    /** Statut courant de l'offre (ex. {@code OPEN}, {@code CLOSED}). */
    private String status;

    /** Identifiant du trader responsable de l'offre. */
    private String trader;

    /** Portefeuille ou livre de trading auquel appartient l'offre. */
    private String book;

    /** Nom de l'utilisateur ayant créé l'offre. */
    @Column(name = "creationName")
    private String creationName;

    /** Date et heure de création de l'offre. */
    @Column(name = "creationDate")
    private LocalDateTime creationDate;

    /** Nom de l'utilisateur ayant effectué la dernière révision. */
    @Column(name = "revisionName")
    private String revisionName;

    /** Date et heure de la dernière révision. */
    @Column(name = "revisionDate")
    private LocalDateTime revisionDate;

    /** Nom du deal associé à l'offre. */
    @Column(name = "dealName")
    private String dealName;

    /** Type du deal associé à l'offre. */
    @Column(name = "dealType")
    private String dealType;

    /** Identifiant de la liste source dont provient l'offre. */
    @Column(name = "sourceListId")
    private String sourceListId;

    /** Côté de l'offre (ex. {@code BUY}, {@code SELL}). */
    private String side;

    /**
     * Crée une offre d'achat avec les trois champs obligatoires.
     *
     * @param account     le compte associé
     * @param type        le type d'instrument
     * @param bidQuantity la quantité proposée à l'achat
     */
    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }
}