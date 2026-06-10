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
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    String account;

    @NotBlank(message = "Type is mandatory")
    String type;

    @NotNull(message = "Buy Quantity is mandatory")
    @PositiveOrZero(message = "Buy Quantity must be numeric and positive or zero")
    Double buyQuantity;

    @PositiveOrZero(message = "Sell Quantity must be numeric and positive or zero")
    Double sellQuantity;

    @PositiveOrZero(message = "Buy Price must be numeric and positive or zero")
    Double buyPrice;

    @PositiveOrZero(message = "Sell Price must be numeric and positive or zero")
    Double sellPrice;
    String benchmark;
    LocalDateTime tradeDate;
    String security;
    String status;
    String trader;
    String book;
    String creationName;
    LocalDateTime creationDate;
    String revisionName;
    LocalDateTime revisionDate;
    String dealName;
    String dealType;
    String sourceListId;
    String side;

    public Trade(String tradeAccount, String type) {
        this.account = tradeAccount;
        this.type = type;
        this.buyQuantity = 0d;
    }
}
