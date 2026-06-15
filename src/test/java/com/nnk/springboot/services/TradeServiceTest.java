package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceTest {
    @Mock
    private TradeRepository repository;

    @InjectMocks
    private TradeService service;

    @Test
    void findAllShouldReturnAllTrades() {
        List<Trade> trades = List.of(new Trade("Account", "Type"));
        when(repository.findAll()).thenReturn(trades);

        assertEquals(trades, service.findAll());
    }

    @Test
    void findByIdShouldReturnTradeWhenFound() {
        Trade trade = new Trade("Account", "Type");
        when(repository.findById(1)).thenReturn(Optional.of(trade));

        assertSame(trade, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldPersistTrade() {
        Trade trade = new Trade("Account", "Type");
        when(repository.save(trade)).thenReturn(trade);

        assertSame(trade, service.save(trade));
    }

    @Test
    void updateShouldCheckExistenceSetIdAndSave() {
        Trade trade = new Trade("New", "Type");
        when(repository.findById(1)).thenReturn(Optional.of(new Trade("Old", "Type")));

        service.update(1, trade);

        assertEquals(1, trade.getTradeId());
        verify(repository).save(trade);
    }

    @Test
    void deleteByIdShouldCheckExistenceAndDelete() {
        when(repository.findById(1)).thenReturn(Optional.of(new Trade("Account", "Type")));

        service.deleteById(1);

        verify(repository).deleteById(1);
    }
}