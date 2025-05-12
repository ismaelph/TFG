package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.CategoriaRepository;
import com.tfg.backend.service.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria save(Categoria categoria) {
        categoriaRepository.save(categoria);
        return categoriaRepository.findById(categoria.getId()).orElse(null);
    }

    @Override
    public Categoria update(Categoria categoriaEditada, Long id) {
        Categoria categoriaBD = categoriaRepository.findById(id).orElse(null);
        if (categoriaBD != null) {
            categoriaBD.setNombre(categoriaEditada.getNombre());
            return categoriaRepository.save(categoriaBD);
        }
        return null;
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public List<Categoria> findByEmpresa(Empresa empresa) {
        return categoriaRepository.findByEmpresa(empresa);
    }

    @Override
    public void delete(Long id) {
        categoriaRepository.deleteById(id);
    }
}
