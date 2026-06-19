package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur affichant la page d'accueil publique de l'application.
 *
 * <p>Cette route ({@code /}) est accessible sans authentification et propose
 * des liens vers le formulaire de connexion ({@code /app/login}) et la gestion
 * des utilisateurs ({@code /user/list}).</p>
 */
@Controller
public class HomeController {

	/**
	 * Affiche la page d'accueil.
	 *
	 * @param model non utilisé ici, conservé pour une extension future de la vue
	 */
	@RequestMapping("/")
	public String home(Model model) {
		return "home";
	}
}