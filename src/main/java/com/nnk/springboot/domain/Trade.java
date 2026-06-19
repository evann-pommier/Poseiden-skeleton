package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull(message = "Buy Quantity is mandatory")
    @PositiveOrZero(message = "Buy Quantity must be numeric and positive or zero")
    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @PositiveOrZero(message = "Sell Quantity must be numeric and positive or zero")
    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @PositiveOrZero(message = "Buy Price must be numeric and positive or zero")
    @Column(name = "buyPrice")
    private Double buyPrice;

    @PositiveOrZero(message = "Sell Price must be numeric and positive or zero")
    @Column(name = "sellPrice")
    private Double sellPrice;
    private String benchmark;
    @Column(name = "tradeDate")
    private LocalDateTime tradeDate;
    private String security;
    private String status;
    private String trader;
    private String book;
    @Column(name = "creationName")
    private String creationName;
    @Column(name = "creationDate")
    private LocalDateTime creationDate;
    @Column(name = "revisionName")
    private String revisionName;
    @Column(name = "revisionDate")
    private LocalDateTime revisionDate;
    @Column(name = "dealName")
    private String dealName;
    @Column(name = "dealType")
    private String dealType;
    @Column(name = "sourceListId")
    private String sourceListId;
    private String side;

    public Trade(String tradeAccount, String type) {
        this.account = tradeAccount;
        this.type = type;
        this.buyQuantity = 0d;
    }
}
