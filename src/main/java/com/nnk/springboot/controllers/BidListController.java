package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.BidListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les offres d'achat ({@link BidList}).
 *
 * <p>Toutes les routes sont protégées par Spring Security. En cas d'identifiant
 * introuvable, le service lève une {@link com.nnk.springboot.exceptions.EntityNotFoundException}
 * qui redirige vers {@code error/404}.</p>
 */
@Controller
public class BidListController {

    private final BidListService service;

    public BidListController(BidListService service) {
        this.service = service;
    }

    /** Affiche la liste de toutes les offres d'achat. */
    @RequestMapping("/bidList/list")
    public String home(Model model) {
        model.addAttribute("bidLists", service.findAll());
        return "bidList/list";
    }

    /** Affiche le formulaire de création d'une nouvelle offre d'achat. */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    /**
     * Valide et persiste une nouvelle offre d'achat.
     * Réaffiche le formulaire en cas d'erreur de validation
     * ({@code @NotBlank} sur {@code account} et {@code type},
     * {@code @PositiveOrZero} sur les montants).
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result) {
        if (!result.hasErrors()) {
            service.save(bid);
            return "redirect:/bidList/list";
        }
        return "bidList/add";
    }

    /** Affiche le formulaire de modification de l'offre identifiée par {@code id}. */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("bidList", service.findById(id));
        return "bidList/update";
    }

    /**
     * Valide et applique la mise à jour de l'offre identifiée par {@code id}.
     * En cas d'erreur, l'identifiant est réaffecté à l'objet avant de
     * réafficher le formulaire.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable Integer id, @Valid BidList bidList, BindingResult result) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }
        service.update(id, bidList);
        return "redirect:/bidList/list";
    }

    /** Supprime l'offre identifiée par {@code id} après vérification de son existence. */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/bidList/list";
    }
}