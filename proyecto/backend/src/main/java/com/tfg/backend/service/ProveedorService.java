package com.tfg.backend.service;

import com.tfg.backend.model.entity.Proveedor;

import java.util.List;

public interface ProveedorService {
    Proveedor save(Proveedor proveedor);
    Proveedor update(Proveedor proveedor, long id);
    void delete(long id);

    List<Proveedor> findAll();
    Proveedor findById(long id);
    Proveedor findByNombre(String nombre);
}
