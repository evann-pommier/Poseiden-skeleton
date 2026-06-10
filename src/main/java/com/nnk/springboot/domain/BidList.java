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
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    String account;

    @NotBlank(message = "Type is mandatory")
    String type;

    @NotNull(message = "Bid Quantity is mandatory")
    @PositiveOrZero(message = "Bid Quantity must be numeric and positive or zero")
    Double bidQuantity;

    @PositiveOrZero(message = "Ask Quantity must be numeric and positive or zero")
    Double askQuantity;

    @PositiveOrZero(message = "Bid must be numeric and positive or zero")
    Double bid;

    @PositiveOrZero(message = "Ask must be numeric and positive or zero")
    Double ask;
    String benchmark;
    LocalDateTime bidListDate;
    String commentary;
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


    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

}
