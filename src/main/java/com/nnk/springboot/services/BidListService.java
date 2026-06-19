package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service métier pour les opérations CRUD sur les {@link BidList}.
 *
 * <p>Les méthodes {@code findById} et {@code deleteById} lèvent une
 * {@link EntityNotFoundException} si l'entité est introuvable.</p>
 */
@Service
public class BidListService {

    private final BidListRepository repository;

    public BidListService(BidListRepository repository) {
        this.repository = repository;
    }

    /**
     * @throws EntityNotFoundException si aucune {@link BidList} n'existe pour cet identifiant
     */
    public BidList findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("BidList", id));
    }

    public List<BidList> findAll() {
        return repository.findAll();
    }

    public BidList save(@NonNull BidList bidList) {
        return repository.save(bidList);
    }

    /**
     * Vérifie l'existence de l'entité avant suppression.
     *
     * @throws EntityNotFoundException si aucune {@link BidList} n'existe pour cet identifiant
     */
    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    /**
     * Met à jour une {@link BidList} existante en forçant son identifiant.
     *
     * @throws EntityNotFoundException si aucune {@link BidList} n'existe pour cet identifiant
     */
    public BidList update(Integer id, @NonNull BidList bidList) {
        findById(id);
        bidList.setBidListId(id);
        return repository.save(bidList);
    }
}