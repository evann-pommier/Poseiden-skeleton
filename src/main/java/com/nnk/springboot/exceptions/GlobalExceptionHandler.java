package com.nnk.springboot.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Gestionnaire global des exceptions MVC, commun à tous les contrôleurs.
 *
 * <p>Intercepte les exceptions applicatives et les traduit en vues d'erreur
 * avec le code HTTP approprié.</p>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Intercepte les {@link EntityNotFoundException} et renvoie une page 404.
     *
     * @param ex    l'exception contenant le message d'erreur
     * @param model le modèle dans lequel le message et le code sont injectés
     * @return la vue {@code error/404}
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("statusCode", HttpStatus.NOT_FOUND.value());
        return "error/404";
    }

    /**
     * Intercepte toute exception non gérée et renvoie une page 500.
     * L'exception est loggée en erreur pour faciliter le diagnostic.
     *
     * @param ex    l'exception inattendue
     * @param model le modèle dans lequel le message et le code sont injectés
     * @return la vue {@code error/500}
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        log.error("Unexpected error occurred", ex);
        model.addAttribute("errorMessage", "An unexpected error occurred.");
        model.addAttribute("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/500";
    }
}