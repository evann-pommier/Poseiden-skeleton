package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository repository;
    public RatingService(RatingRepository repository){
        this.repository = repository;
    }

    public List<Rating> findAll(){
        return repository.findAll();
    }

    public Rating findById(Integer id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Rating:", id));
    }

    public Rating save(Rating rating){
        return repository.save(rating);
    }

    public Rating update(Integer id, Rating rating) {
        rating.setId(id);
        return repository.save(rating);
    }
    public void deleteById(Integer id) {
        repository.delete(findById(id));
    }
}
