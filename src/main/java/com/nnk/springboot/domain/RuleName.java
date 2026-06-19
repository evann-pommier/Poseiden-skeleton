package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

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
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Description is mandatory")
    private String description;
    @NotBlank(message = "Json is mandatory")
    private String json;
    @NotBlank(message = "Template is mandatory")
    private String template;
    @NotBlank(message = "SqlStr is mandatory")
    @Column(name = "sqlStr")
    private String sqlStr;
    @NotBlank(message = "SqlPart is mandatory")
    @Column(name = "sqlPart")
    private String sqlPart;

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public String getSql() { return sqlStr; }

}
