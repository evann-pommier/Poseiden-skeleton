package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.RatingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les notations financières ({@link Rating}).
 *
 * <p>Un {@link Rating} agrège les notations des trois principales agences :
 * Moody's ({@code moodysRating}), Standard &amp; Poor's ({@code sandPRating})
 * et Fitch ({@code fitchRating}), complétées par un numéro d'ordre
 * ({@code orderNumber}). En cas d'identifiant introuvable, le service lève
 * une {@link com.nnk.springboot.exceptions.EntityNotFoundException} qui
 * redirige vers {@code error/404}.</p>
 */
@Controller
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService ratingService) {
        this.service = ratingService;
    }

    /** Affiche la liste de toutes les notations financières. */
    @GetMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", service.findAll());
        return "rating/list";
    }

    /** Affiche le formulaire de création d'une nouvelle notation financière. */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    /**
     * Valide et persiste une nouvelle notation financière.
     * Réaffiche le formulaire en cas d'erreur de validation ({@code @NotBlank}
     * sur les trois notations d'agence, {@code @NotNull} et {@code @Positive}
     * sur {@code orderNumber}).
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        service.save(rating);
        return "redirect:/rating/list";
    }

    /** Affiche le formulaire de modification de la notation identifiée par {@code id}. */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("rating", service.findById(id));
        return "rating/update";
    }

    /**
     * Valide et applique la mise à jour de la notation identifiée par {@code id}.
     * En cas d'erreur, l'identifiant est réaffecté à l'objet avant de
     * réafficher le formulaire.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable Integer id, @Valid Rating rating, BindingResult result) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }
        service.update(id, rating);
        return "redirect:/rating/list";
    }

    /** Supprime la notation identifiée par {@code id} après vérification de son existence. */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/rating/list";
    }
}