package com.tfg.backend.service;

import com.tfg.backend.model.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoService {
    Producto save(Producto producto);
    Producto update(Producto producto, long id);
    void delete(long id);

    List<Producto> findAll();
    Producto findById(long id);
    Producto findByNombre(String nombre);
    List<Producto> findByCategoriaNombre(String nombreCategoria);
    List<Producto> findByEmpresaId(Long empresaId);
}
