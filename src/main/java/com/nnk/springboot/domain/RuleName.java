package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Entité représentant une règle métier nommée, associant une définition JSON,
 * un template de rendu et des fragments SQL.
 *
 * <p>{@code sqlStr} contient la requête SQL complète tandis que {@code sqlPart}
 * en isole un fragment réutilisable, par exemple une clause {@code WHERE}.</p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "RuleName")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    /** Nom de la règle. Obligatoire. */
    @NotBlank(message = "Name is mandatory")
    private String name;

    /** Description fonctionnelle de la règle. Obligatoire. */
    @NotBlank(message = "Description is mandatory")
    private String description;

    /** Définition de la règle au format JSON. Obligatoire. */
    @NotBlank(message = "Json is mandatory")
    private String json;

    /** Template de rendu associé à la règle. Obligatoire. */
    @NotBlank(message = "Template is mandatory")
    private String template;

    /** Requête SQL complète associée à la règle. Obligatoire. */
    @NotBlank(message = "SqlStr is mandatory")
    @Column(name = "sqlStr")
    private String sqlStr;

    /** Fragment SQL partiel (ex. clause WHERE) réutilisable. Obligatoire. */
    @NotBlank(message = "SqlPart is mandatory")
    @Column(name = "sqlPart")
    private String sqlPart;

    /**
     * Crée un {@code RuleName} avec l'ensemble de ses champs obligatoires.
     *
     * @param name        nom de la règle
     * @param description description fonctionnelle
     * @param json        définition JSON
     * @param template    template de rendu
     * @param sqlStr      requête SQL complète
     * @param sqlPart     fragment SQL partiel
     */
    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    /** Alias de {@code getMoodysRating()} — retourne la requête SQL complète ({@code sqlStr}). */
    public String getSql() { return sqlStr; }
}