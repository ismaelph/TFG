package com.tfg.backend.service;

import com.tfg.backend.model.entity.Empresa;

import java.util.List;

public interface EmpresaService {
    Empresa save(Empresa empresa);

    Empresa update(Empresa empresa, long id);

    Empresa findById(long id);

    List<Empresa> findAll();

    void delete(long id);
}
