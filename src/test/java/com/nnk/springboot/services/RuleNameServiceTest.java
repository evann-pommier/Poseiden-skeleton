package com.nnk.springboot.services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {
    @Mock
    private RuleNameRepository repository;

    @InjectMocks
    private RuleNameService service;

    @Test
    void findAllShouldReturnAllRuleNames() {
        List<RuleName> ruleNames = List.of(new RuleName("Name", "Description", "{}", "Template", "SQL", "Part"));
        when(repository.findAll()).thenReturn(ruleNames);

        assertEquals(ruleNames, service.findAll());
    }

    @Test
    void findByIdShouldReturnRuleNameWhenFound() {
        RuleName ruleName = new RuleName("Name", "Description", "{}", "Template", "SQL", "Part");
        when(repository.findById(1)).thenReturn(Optional.of(ruleName));

        assertSame(ruleName, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldPersistRuleName() {
        RuleName ruleName = new RuleName("Name", "Description", "{}", "Template", "SQL", "Part");
        when(repository.save(ruleName)).thenReturn(ruleName);

        assertSame(ruleName, service.save(ruleName));
    }

    @Test
    void updateShouldSetIdAndSave() {
        RuleName ruleName = new RuleName("Name", "Description", "{}", "Template", "SQL", "Part");
        when(repository.findById(1)).thenReturn(Optional.of(ruleName));
        when(repository.save(ruleName)).thenReturn(ruleName);

        RuleName result = service.update(1, ruleName);

        assertSame(ruleName, result);
        assertEquals(1, ruleName.getId());
        verify(repository).save(ruleName);
    }

    @Test
    void deleteByIdShouldDeleteById() {
        service.deleteById(1);

        verify(repository).deleteById(1);
    }
}