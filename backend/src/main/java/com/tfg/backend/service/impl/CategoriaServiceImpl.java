package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.repository.CategoriaRepository;
import com.tfg.backend.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;


    @Override
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria, long id) {
        Categoria categoriaBD = categoriaRepository.findById(id);
        if (categoriaBD != null) {
            categoriaBD.setNombre(categoria.getNombre());
            return categoriaRepository.save(categoriaBD);
        }
        return categoriaBD;
    }

    @Override
    public void delete(long id) {
        categoriaRepository.deleteById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria findById(long id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Categoria findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
}
