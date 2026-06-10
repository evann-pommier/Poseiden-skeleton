package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {
    private final TradeRepository repository;

    public TradeService(TradeRepository tradeRepository) {
        this.repository = tradeRepository;
    }

    public List<Trade> findAll() {
        return this.repository.findAll();
    }

    public Trade findById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("TradeList", id));
    }

    public Trade save(Trade trade) {
        return this.repository.save(trade);
    }
    public void update(Trade trade) {
        this.repository.save(trade);
    }
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }
}
