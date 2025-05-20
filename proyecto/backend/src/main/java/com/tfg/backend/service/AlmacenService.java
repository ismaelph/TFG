package com.tfg.backend.service;

import com.tfg.backend.model.entity.Almacen;

import java.util.List;

public interface AlmacenService {
    Almacen save(Almacen almacen);
    Almacen update(Almacen almacen, long id);
    Almacen findById(long id);
    List<Almacen> findAll();
    void delete(long id);
    List<Almacen> findByEmpresaId(long empresaId);
}
