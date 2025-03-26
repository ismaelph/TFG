package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.repository.CategoriasRepository;
import com.tfg.backend.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriasService {
    @Autowired
    private CategoriasRepository categoriasRepository;


    @Override
    public Categoria save(Categoria categoria) {
        return categoriasRepository.save(categoria);
    }

    @Override
    public Categoria update(Categoria categoria, long id) {
        Categoria categoriaBD = categoriasRepository.findById(id);
        if (categoriaBD != null) {
            categoriaBD.setNombre(categoria.getNombre());
            categoriaBD.setDescripcion(categoria.getDescripcion());
            categoriasRepository.save(categoriaBD);
        }
        return categoriaBD;
    }

    @Override
    public void delete(long id) {
        categoriasRepository.deleteById(id);
    }

    @Override
    public List<Categoria> findAll() {
        return categoriasRepository.findAll();
    }

    @Override
    public Categoria findById(long id) {
        return categoriasRepository.findById(id);
    }

    @Override
    public Categoria findByNombre(String nombre) {
        return categoriasRepository.findByNombre(nombre);
    }
}
