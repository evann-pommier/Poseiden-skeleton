package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidListServiceTest {
    @Mock
    private BidListRepository repository;

    @InjectMocks
    private BidListService service;

    @Test
    void findAllShouldReturnAllBidLists() {
        List<BidList> bidLists = List.of(new BidList("Account", "Type", 10d));
        when(repository.findAll()).thenReturn(bidLists);

        assertEquals(bidLists, service.findAll());
        verify(repository).findAll();
    }

    @Test
    void findByIdShouldReturnBidListWhenFound() {
        BidList bidList = new BidList("Account", "Type", 10d);
        when(repository.findById(1)).thenReturn(Optional.of(bidList));

        assertSame(bidList, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldPersistBidList() {
        BidList bidList = new BidList("Account", "Type", 10d);
        when(repository.save(bidList)).thenReturn(bidList);

        assertSame(bidList, service.save(bidList));
        verify(repository).save(bidList);
    }

    @Test
    void updateShouldCheckExistenceSetIdAndSave() {
        BidList bidList = new BidList("New", "Type", 10d);
        when(repository.findById(1)).thenReturn(Optional.of(new BidList("Old", "Type", 1d)));
        when(repository.save(bidList)).thenReturn(bidList);

        BidList result = service.update(1, bidList);

        assertSame(bidList, result);
        assertEquals(1, bidList.getBidListId());
        verify(repository).save(bidList);
    }

    @Test
    void deleteByIdShouldCheckExistenceAndDelete() {
        when(repository.findById(1)).thenReturn(Optional.of(new BidList("Account", "Type", 10d)));
        service.deleteById(1);
        verify(repository).deleteById(1);
    }
}
