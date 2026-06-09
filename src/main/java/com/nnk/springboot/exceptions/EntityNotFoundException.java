package com.nnk.springboot.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Integer id) {
        super(entityName + " not found with id: " + id);
    }
}