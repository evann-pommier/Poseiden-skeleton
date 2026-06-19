package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * Contrôleur CRUD pour les règles métier ({@link RuleName}).
 *
 * <p>Un {@link RuleName} définit une règle de traitement identifiée par un nom,
 * une description, un template, une représentation JSON et des fragments SQL
 * ({@code sqlStr}, {@code sqlPart}). En cas d'identifiant introuvable, le service
 * lève une {@link com.nnk.springboot.exceptions.EntityNotFoundException} qui
 * redirige vers {@code error/404}.</p>
 *
 * <p>Le service est injecté par setter ({@link #setRuleNameService}) plutôt que
 * par constructeur, contrairement aux autres contrôleurs du projet.</p>
 */
@Controller
public class RuleNameController {

    RuleNameService service;

    /**
     * Injecte le service métier par setter.
     *
     * @param ruleNameService le service {@link RuleNameService} à injecter
     */
    @Autowired
    public void setRuleNameService(RuleNameService ruleNameService) {
        this.service = ruleNameService;
    }

    /** Affiche la liste de toutes les règles métier. */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", service.findAll());
        return "ruleName/list";
    }

    /**
     * Affiche le formulaire de création d'une nouvelle règle métier.
     *
     * <p>L'objet {@link RuleName} est lié directement via le paramètre de méthode
     * (model attribute implicite), sans injection explicite dans un {@code Model}.</p>
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName ruleName) {
        return "ruleName/add";
    }

    /**
     * Valide et persiste une nouvelle règle métier.
     * Réaffiche le formulaire en cas d'erreur de validation
     * ({@code @NotBlank} sur les six champs : {@code name}, {@code description},
     * {@code json}, {@code template}, {@code sqlStr}, {@code sqlPart}).
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        service.save(ruleName);
        return "redirect:/ruleName/list";
    }

    /** Affiche le formulaire de modification de la règle identifiée par {@code id}. */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("ruleName", service.findById(id));
        return "ruleName/update";
    }

    /**
     * Valide et applique la mise à jour de la règle identifiée par {@code id}.
     * En cas d'erreur, l'identifiant est réaffecté à l'objet avant de
     * réafficher le formulaire.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable Integer id, @Valid RuleName ruleName, BindingResult result) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }
        service.update(id, ruleName);
        return "redirect:/ruleName/list";
    }

    /** Supprime la règle identifiée par {@code id} après vérification de son existence. */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable Integer id) {
        service.deleteById(id);
        return "redirect:/ruleName/list";
    }
}