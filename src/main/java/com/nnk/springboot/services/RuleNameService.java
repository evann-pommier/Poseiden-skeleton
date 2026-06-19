package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link RuleName}.
 *
 * <p>Les méthodes {@code findById} et {@code deleteById} lèvent une
 * {@link EntityNotFoundException} si l'entité est introuvable.</p>
 */
@Service
public class RuleNameService {

    private final RuleNameRepository repository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.repository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return repository.findAll();
    }

    /**
     * @throws EntityNotFoundException si aucun {@link RuleName} n'existe pour cet identifiant
     */
    public RuleName findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("RuleName ", id));
    }

    public RuleName save(RuleName ruleName) {
        return repository.save(ruleName);
    }

    /**
     * Met à jour un {@link RuleName} existant en forçant son identifiant.
     *
     * @throws EntityNotFoundException si aucun {@link RuleName} n'existe pour cet identifiant
     */
    public RuleName update(Integer id, RuleName ruleName) {
        findById(id);
        ruleName.setId(id);
        return repository.save(ruleName);
    }

    /**
     * @throws EntityNotFoundException si aucun {@link RuleName} n'existe pour cet identifiant
     */
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}