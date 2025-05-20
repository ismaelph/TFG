package com.tfg.backend.service;

import com.tfg.backend.model.entity.Estanteria;

import java.util.List;

public interface EstanteriaService {
    Estanteria save(Estanteria estanteria);
    Estanteria update(Estanteria estanteria, long id);
    Estanteria findById(long id);
    List<Estanteria> findAll();
    void delete(long id);
    List<Estanteria> findByPlantaId(long plantaId);
}
