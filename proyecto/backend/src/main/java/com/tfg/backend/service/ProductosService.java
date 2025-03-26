package com.tfg.backend.service;

import com.tfg.backend.model.entity.Producto;

import java.util.List;

public interface ProductosService {
    Producto save(Producto producto);
    Producto update(Producto producto, long id);
    void delete(long id);

    List<Producto> findAll();
    Producto findById(long id);
    List<Producto> findByNombre(String nombre);
}
