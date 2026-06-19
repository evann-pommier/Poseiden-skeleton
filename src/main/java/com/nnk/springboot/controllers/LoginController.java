package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Contrôleur affichant la page de connexion de l'application.
 *
 * <p>Ce contrôleur se limite au rendu de la vue. Le traitement du formulaire
 * (POST sur {@code /app/login}) est délégué à Spring Security
 * ({@link com.nnk.springboot.security.SecurityConfig}). La vue gère les
 * paramètres {@code error}, {@code logout}, {@code expired} et
 * {@code invalidSession} transmis par Spring Security.</p>
 */
@Controller
@RequestMapping("app")
public class LoginController {

    /**
     * Affiche le formulaire de connexion.
     *
     * <p>En cas de succès, Spring Security redirige automatiquement
     * vers {@code /bidList/list}.</p>
     */
    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }
}