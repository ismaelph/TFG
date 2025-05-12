package com.tfg.backend.service;

import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.model.entity.Empresa;

import java.util.List;

public interface ProveedorService {

    Proveedor save(Proveedor proveedor);

    Proveedor update(Proveedor proveedor, Long id);

    Proveedor findById(Long id);

    List<Proveedor> findAll();

    List<Proveedor> findByEmpresa(Empresa empresa);

    void delete(Long id);
}
