package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link Trade}.
 *
 * <p>Les méthodes {@code findById} et {@code deleteById} lèvent une
 * {@link EntityNotFoundException} si l'entité est introuvable.</p>
 */
@Service
public class TradeService {

    private final TradeRepository repository;

    public TradeService(TradeRepository tradeRepository) {
        this.repository = tradeRepository;
    }

    public List<Trade> findAll() {
        return this.repository.findAll();
    }

    /**
     * @throws EntityNotFoundException si aucun {@link Trade} n'existe pour cet identifiant
     */
    public Trade findById(int id) {
        return this.repository.findById(id).orElseThrow(() -> new EntityNotFoundException("TradeList", id));
    }

    public Trade save(Trade trade) {
        return this.repository.save(trade);
    }

    /**
     * Met à jour un {@link Trade} existant en forçant son identifiant.
     *
     * @throws EntityNotFoundException si aucun {@link Trade} n'existe pour cet identifiant
     */
    public void update(Integer id, Trade trade) {
        findById(id);
        trade.setTradeId(id);
        this.repository.save(trade);
    }

    /**
     * Vérifie l'existence de l'entité avant suppression.
     *
     * @throws EntityNotFoundException si aucun {@link Trade} n'existe pour cet identifiant
     */
    public void deleteById(Integer id) {
        findById(id);
        this.repository.deleteById(id);
    }
}