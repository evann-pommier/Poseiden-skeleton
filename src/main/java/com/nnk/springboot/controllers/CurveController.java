package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.CurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les points de courbe de taux ({@link CurvePoint}).
 *
 * <p>Un {@link CurvePoint} représente un point sur une courbe de taux d'intérêt,
 * défini par un identifiant de courbe ({@code curveId}), une échéance ({@code term})
 * et une valeur ({@code value}). En cas d'identifiant introuvable, le service lève
 * une {@link com.nnk.springboot.exceptions.EntityNotFoundException} qui redirige
 * vers {@code error/404}.</p>
 */
@Controller
public class CurveController {

    private final CurveService service;

    @Autowired
    public CurveController(CurveService service) {
        this.service = service;
    }

    /** Affiche la liste de tous les points de courbe. */
    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", service.findAll());
        return "curvePoint/list";
    }

    /** Affiche le formulaire de création d'un nouveau point de courbe. */
    @GetMapping("/curvePoint/add")
    public String addBidForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    /**
     * Valide et persiste un nouveau point de courbe.
     * Réaffiche le formulaire en cas d'erreur de validation
     * ({@code @NotNull} sur {@code curveId}, {@code term} et {@code value}).
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        service.save(curvePoint);
        return "redirect:/curvePoint/list";
    }

    /** Affiche le formulaire de modification du point identifié par {@code id}. */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("curvePoint", service.findById(id));
        return "curvePoint/update";
    }

    /**
     * Valide et applique la mise à jour du point identifié par {@code id}.
     * En cas d'erreur, l'identifiant est réaffecté à l'objet avant de
     * réafficher le formulaire.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable Integer id, @Valid CurvePoint curvePoint, BindingResult result) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        service.update(id, curvePoint);
        return "redirect:/curvePoint/list";
    }

    /** Supprime le point identifié par {@code id} après vérification de son existence. */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/curvePoint/list";
    }
}