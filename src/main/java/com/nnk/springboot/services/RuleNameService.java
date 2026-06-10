package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    private final RuleNameRepository repository;
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.repository = ruleNameRepository;
    }

    public List<RuleName> findAll() {
        return repository.findAll();
    }

    public RuleName findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("RuleName ", id));
    }

    public RuleName save(RuleName ruleName) {
        return repository.save(ruleName);
    }

    public RuleName update(Integer id, RuleName ruleName) {
        ruleName.setId(id);
        return repository.save(ruleName);
    }
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
