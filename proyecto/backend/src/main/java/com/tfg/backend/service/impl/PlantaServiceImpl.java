package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Planta;
import com.tfg.backend.model.repository.PlantaRepository;
import com.tfg.backend.service.PlantaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlantaServiceImpl implements PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    @Override
    public Planta save(Planta planta) {
        return plantaRepository.save(planta);
    }

    @Override
    public Planta update(Planta planta, long id) {
        Planta actual = plantaRepository.findById(id).orElse(null);
        if (actual != null) {
            actual.setNombre(planta.getNombre());
            actual.setNumero(planta.getNumero());
            return plantaRepository.save(actual);
        }
        return null;
    }

    @Override
    public Planta findById(long id) {
        return plantaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Planta> findAll() {
        return plantaRepository.findAll();
    }

    @Override
    public void delete(long id) {
        plantaRepository.deleteById(id);
    }

    @Override
    public List<Planta> findByAlmacenId(long almacenId) {
        return plantaRepository.findByAlmacenId(almacenId);
    }
}
