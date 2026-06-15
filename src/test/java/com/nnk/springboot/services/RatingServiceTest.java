package com.nnk.springboot.services;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingServiceTest {
    @Mock
    private RatingRepository repository;

    @InjectMocks
    private RatingService service;

    @Test
    void findAllShouldReturnAllRatings() {
        List<Rating> ratings = List.of(new Rating("A", "A", "A", 1));
        when(repository.findAll()).thenReturn(ratings);

        assertEquals(ratings, service.findAll());
    }

    @Test
    void findByIdShouldReturnRatingWhenFound() {
        Rating rating = new Rating("A", "A", "A", 1);
        when(repository.findById(1)).thenReturn(Optional.of(rating));

        assertSame(rating, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldPersistRating() {
        Rating rating = new Rating("A", "A", "A", 1);
        when(repository.save(rating)).thenReturn(rating);

        assertSame(rating, service.save(rating));
    }

    @Test
    void updateShouldSetIdAndSave() {
        Rating rating = new Rating("B", "B", "B", 2);
        when(repository.save(rating)).thenReturn(rating);

        Rating result = service.update(1, rating);

        assertSame(rating, result);
        assertEquals(1, rating.getId());
        verify(repository).save(rating);
    }

    @Test
    void deleteByIdShouldDeleteFoundRating() {
        Rating rating = new Rating("A", "A", "A", 1);
        when(repository.findById(1)).thenReturn(Optional.of(rating));

        service.deleteById(1);

        verify(repository).delete(rating);
    }
}
