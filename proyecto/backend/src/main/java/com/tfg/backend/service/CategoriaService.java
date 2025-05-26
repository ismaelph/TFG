package com.tfg.backend.service;

import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.entity.Empresa;

import java.util.List;

public interface CategoriaService {

    Categoria save(Categoria categoria);

    Categoria update(Categoria categoria, Long id);

    Categoria findById(Long id);

    List<Categoria> findAll();

    List<Categoria> findByEmpresa(Empresa empresa);

    void delete(Long id);


}
