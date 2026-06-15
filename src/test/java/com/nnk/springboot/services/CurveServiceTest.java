package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
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
public class CurveServiceTest {
    @Mock
    private CurvePointRepository repository;

    @InjectMocks
    private CurveService service;

    @Test
    void findAllShouldReturnAllCurvePoints() {
        List<CurvePoint> curvePoints = List.of(new CurvePoint(10,2d,30d));
        when(repository.findAll()).thenReturn(curvePoints);

        assertEquals(curvePoints, service.findAll());
    }

    @Test
    void findByIdShouldReturnCurvePointWhenFound() {
        CurvePoint curvePoint = new CurvePoint(10, 2d, 30d);
        when(repository.findById(1)).thenReturn(Optional.of(curvePoint));

        assertSame(curvePoint, service.findById(1));
    }

    @Test
    void findByIdShouldThrowWhenNotFound() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(99));
    }

    @Test
    void saveShouldPersistCurvePoint() {
        CurvePoint curvePoint = new CurvePoint(10, 2d, 30d);

        service.save(curvePoint);

        verify(repository).save(curvePoint);
    }

    @Test
    void updateShouldCheckExistenceSetIdAndSave() {
        CurvePoint curvePoint = new CurvePoint(11, 3d, 40d);
        when(repository.findById(1)).thenReturn(Optional.of(new CurvePoint(10, 2d, 30d)));
        when(repository.save(curvePoint)).thenReturn(curvePoint);

        CurvePoint result = service.update(1, curvePoint);

        assertSame(curvePoint, result);
        assertEquals(1, curvePoint.getId());
        verify(repository).save(curvePoint);
    }

    @Test
    void deleteByIdShouldCheckExistenceAndDelete() {
        when(repository.findById(1)).thenReturn(Optional.of(new CurvePoint(10, 2d, 30d)));

        service.deleteById(1);

        verify(repository).deleteById(1);
    }
}
