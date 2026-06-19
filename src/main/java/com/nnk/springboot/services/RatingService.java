package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link Rating}.
 *
 * <p>Les méthodes {@code findById} et {@code deleteById} lèvent une
 * {@link EntityNotFoundException} si l'entité est introuvable.</p>
 */
@Service
public class RatingService {

    private final RatingRepository repository;

    public RatingService(RatingRepository repository) {
        this.repository = repository;
    }

    public List<Rating> findAll() {
        return repository.findAll();
    }

    /**
     * @throws EntityNotFoundException si aucun {@link Rating} n'existe pour cet identifiant
     */
    public Rating findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating:", id));
    }

    public Rating save(Rating rating) {
        return repository.save(rating);
    }

    /**
     * Met à jour un {@link Rating} existant en forçant son identifiant.
     *
     * @throws EntityNotFoundException si aucun {@link Rating} n'existe pour cet identifiant
     */
    public Rating update(Integer id, Rating rating) {
        findById(id);
        rating.setId(id);
        return repository.save(rating);
    }

    /**
     * Vérifie l'existence de l'entité avant suppression.
     *
     * @throws EntityNotFoundException si aucun {@link Rating} n'existe pour cet identifiant
     */
    public void deleteById(Integer id) {
        repository.delete(findById(id));
    }
}