package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto, long id) {
        return null;
    }

    @Override
    public void delete(long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto findById(long id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> findByCategoriaNombre(String nombreCategoria) {
        return productoRepository.findByCategoriaNombre(nombreCategoria);
    }

    @Override
    public List<Producto> findByEmpresaId(Long empresaId) {
        return productoRepository.findByEmpresaId(empresaId);
    }
}
