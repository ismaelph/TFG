package com.tfg.backend.service;

import com.tfg.backend.model.entity.Planta;

import java.util.List;

public interface PlantaService {
    Planta save(Planta planta);
    Planta update(Planta planta, long id);
    Planta findById(long id);
    List<Planta> findAll();
    void delete(long id);
    List<Planta> findByAlmacenId(long almacenId);
}
