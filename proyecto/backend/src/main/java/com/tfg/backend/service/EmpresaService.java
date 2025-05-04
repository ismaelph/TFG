package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;

import java.util.List;

public interface EmpresaService {
    Empresa save(Empresa empresa);

    Empresa update(Empresa empresa, long id);

    void delete(long id);

    Empresa findById(long id);

    List<Empresa> findAll();

    Empresa findByNombre(String nombre);

    void agregarAdminAEmpresa(Empresa empresa, User admin);

    void eliminarAdminDeEmpresa(Empresa empresa, User admin);
}
