package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.exceptions.EntityNotFoundException;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurveService {
    private final CurvePointRepository repository;
    public CurveService(CurvePointRepository curvePointRepository) {
        this.repository = curvePointRepository;
    }

    public List<CurvePoint> findAll() {
        return repository.findAll();
    }
    public CurvePoint findById(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("CurvePoint", id));
    }

    public void save(CurvePoint curvePoint) {
        repository.save(curvePoint);
    }
    public CurvePoint update(Integer id,CurvePoint curvePoint) {
        findById(id);
        curvePoint.setId(id);
        return repository.save(curvePoint);
    }
    public void deleteById(int id) {
        findById(id);
        repository.deleteById(id);
    }
}
