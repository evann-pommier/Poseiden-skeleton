package com.nnk.springboot.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RatingId")
    Integer id;
    @NotBlank(message = "Moodys Rating is mandatory")
    String moodysRating;
    @NotBlank(message = "SandP Rating is mandatory")
    String sandPRating;
    @NotBlank(message = "Fitch Rating is mandatory")
    String fitchRating;
    @NotNull(message = "Order Number is mandatory")
    @Positive(message = "Order Number must be a positive number")
    Integer orderNumber;

    public Rating(String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }
}
