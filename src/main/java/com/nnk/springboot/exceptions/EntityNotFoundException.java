package com.nnk.springboot.exceptions;

/**
 * Exception levée lorsqu'une entité est introuvable en base pour un identifiant donné.
 */
public class EntityNotFoundException extends RuntimeException {

    /**
     * @param entityName nom de l'entité concernée (ex. {@code "BidList"})
     * @param id         identifiant recherché
     */
    public EntityNotFoundException(String entityName, Integer id) {
        super(entityName + " not found with id: " + id);
    }
}