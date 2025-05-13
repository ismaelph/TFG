package com.tfg.backend.service;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Producto;

import java.util.List;

public interface ProductoService {
    Producto save(Producto producto);
    Producto update(Producto producto, Long id);
    void delete(Long id);
    Producto findById(Long id);
    List<Producto> findAll();
    List<Producto> findByEmpresa(Empresa empresa);
}
