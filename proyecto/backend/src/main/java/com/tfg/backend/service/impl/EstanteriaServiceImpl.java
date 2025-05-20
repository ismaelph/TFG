package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Estanteria;
import com.tfg.backend.model.repository.EstanteriaRepository;
import com.tfg.backend.service.EstanteriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class EstanteriaServiceImpl implements EstanteriaService {

    @Autowired
    private EstanteriaRepository estanteriaRepository;

    @Override
    public Estanteria save(Estanteria estanteria) {
        return estanteriaRepository.save(estanteria);
    }

    @Override
    public Estanteria update(Estanteria estanteria, long id) {
        Estanteria actual = estanteriaRepository.findById(id).orElse(null);
        if (actual != null) {
            actual.setCodigo(estanteria.getCodigo());
            return estanteriaRepository.save(actual);
        }
        return null;
    }

    @Override
    public Estanteria findById(long id) {
        return estanteriaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Estanteria> findAll() {
        return estanteriaRepository.findAll();
    }

    @Override
    public void delete(long id) {
        estanteriaRepository.deleteById(id);
    }

    @Override
    public List<Estanteria> findByPlantaId(long plantaId) {
        return estanteriaRepository.findByPlantaId(plantaId);
    }
}
