package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RatingController {
    private final RatingService service;
    public RatingController(RatingService ratingService) {
        this.service = ratingService;
    }

    @GetMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("ratings", service.findAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm() {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        service.save(rating);
        return "redirect:/rating/list";
    }


    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("rating", service.findById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable Integer id, @Valid Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }
        service.update(id,rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/rating/list";
    }
}