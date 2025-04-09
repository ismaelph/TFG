package com.tfg.backend.service;

import com.tfg.backend.model.entity.Categoria;

import java.util.List;

public interface CategoriaService {
    Categoria save(Categoria categoria);
    Categoria update(Categoria categoria, long id);
    void delete(long id);

    List<Categoria> findAll();
    Categoria findById(long id);
    Categoria findByNombre(String nombre);
}
