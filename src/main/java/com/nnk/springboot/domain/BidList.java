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
@Table(name = "BidList")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;

    @NotBlank(message = "Account is mandatory")
    private String account;

    @NotBlank(message = "Type is mandatory")
    private String type;

    @NotNull
    @PositiveOrZero(message = "Bid Quantity must be numeric and positive or zero")
    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @PositiveOrZero(message = "Ask Quantity must be numeric and positive or zero")
    @Column(name = "askQuantity")
    private Double askQuantity;

    @PositiveOrZero(message = "Bid must be numeric and positive or zero")
    private Double bid;

    @PositiveOrZero(message = "Ask must be numeric and positive or zero")
    private Double ask;
    private String benchmark;
    @Column(name = "bidListDate")
    private LocalDateTime bidListDate;
    private String commentary;
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


    public BidList(String account, String type, Double bidQuantity) {
        this.account = account;
        this.type = type;
        this.bidQuantity = bidQuantity;
    }

}
