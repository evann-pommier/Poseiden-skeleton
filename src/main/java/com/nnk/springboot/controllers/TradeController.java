package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.TradeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les transactions financières ({@link Trade}).
 *
 * <p>Un {@link Trade} représente une transaction avec un compte ({@code account}),
 * un type ({@code type}) et une quantité d'achat obligatoire ({@code buyQuantity}).
 * En cas d'identifiant introuvable, le service lève une
 * {@link com.nnk.springboot.exceptions.EntityNotFoundException} qui redirige
 * vers {@code error/404}.</p>
 */
@Controller
public class TradeController {

    private final TradeService service;

    public TradeController(TradeService tradeService) {
        this.service = tradeService;
    }

    /** Affiche la liste de toutes les transactions. */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", this.service.findAll());
        return "trade/list";
    }

    /** Affiche le formulaire de création d'une nouvelle transaction. */
    @GetMapping("/trade/add")
    public String addTrade(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    /**
     * Valide et persiste une nouvelle transaction.
     * Réaffiche le formulaire en cas d'erreur de validation ({@code @NotBlank}
     * sur {@code account} et {@code type}, {@code @NotNull} et
     * {@code @PositiveOrZero} sur {@code buyQuantity}).
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        service.save(trade);
        return "redirect:/trade/list";
    }

    /** Affiche le formulaire de modification de la transaction identifiée par {@code id}. */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("trade", this.service.findById(id));
        return "trade/update";
    }

    /**
     * Valide et applique la mise à jour de la transaction identifiée par {@code id}.
     * En cas d'erreur, l'identifiant est réaffecté à l'objet avant de réafficher
     * le formulaire. Le paramètre {@code model} est déclaré mais non utilisé.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable Integer id, @Valid Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }
        service.update(id, trade);
        return "redirect:/trade/list";
    }

    /** Supprime la transaction identifiée par {@code id} après vérification de son existence. */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/trade/list";
    }
}