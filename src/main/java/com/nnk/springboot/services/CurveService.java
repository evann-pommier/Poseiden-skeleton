package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link CurvePoint}.
 *
 * <p>Les méthodes {@code findById} et {@code deleteById} lèvent une
 * {@link EntityNotFoundException} si l'entité est introuvable.</p>
 */
@Service
public class CurveService {

    private final CurvePointRepository repository;

    public CurveService(CurvePointRepository curvePointRepository) {
        this.repository = curvePointRepository;
    }

    public List<CurvePoint> findAll() {
        return repository.findAll();
    }

    /**
     * @throws EntityNotFoundException si aucun {@link CurvePoint} n'existe pour cet identifiant
     */
    public CurvePoint findById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("CurvePoint", id));
    }

    public void save(CurvePoint curvePoint) {
        repository.save(curvePoint);
    }

    /**
     * Met à jour un {@link CurvePoint} existant en forçant son identifiant.
     *
     * @throws EntityNotFoundException si aucun {@link CurvePoint} n'existe pour cet identifiant
     */
    public CurvePoint update(Integer id, CurvePoint curvePoint) {
        findById(id);
        curvePoint.setId(id);
        return repository.save(curvePoint);
    }

    /**
     * Vérifie l'existence de l'entité avant suppression.
     *
     * @throws EntityNotFoundException si aucun {@link CurvePoint} n'existe pour cet identifiant
     */
    public void deleteById(int id) {
        findById(id);
        repository.deleteById(id);
    }
}