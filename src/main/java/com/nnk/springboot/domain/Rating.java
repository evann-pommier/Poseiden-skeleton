package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

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
    @NotBlank(message = "Moodys Rating is mandatory")
    @Column(name = "moodysRating")
    private String moodysRating;
    @NotBlank(message = "SandP Rating is mandatory")
    @Column(name = "sandPRating")
    private String sandPRating;
    @NotBlank(message = "Fitch Rating is mandatory")
    @Column(name = "fitchRating")
    private String fitchRating;
    @NotNull(message = "Order Number is mandatory")
    @Positive(message = "Order Number must be a positive number")
    @Column(name = "orderNumber")
    private Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
