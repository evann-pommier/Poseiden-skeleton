package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BidListService {
    private final BidListRepository repository;


    public BidListService(BidListRepository repository) {
        this.repository = repository;
    }

    public BidList findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("BidList", id));
    }

    public List<BidList> findAll() {
        return repository.findAll();
    }

    public BidList save(@NonNull BidList bidList) {
        return repository.save(bidList);
    }

    public void deleteById(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    public BidList update(Integer id, @NonNull BidList bidList) {
        findById(id);
        bidList.setBidListId(id);
        return repository.save(bidList);
    }
}