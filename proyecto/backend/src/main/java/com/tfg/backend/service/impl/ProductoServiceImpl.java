package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.repository.ProductosRepository;
import com.tfg.backend.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductosService {
    @Autowired
    private ProductosRepository productosRepository;


    @Override
    public Producto save(Producto producto) {
        return productosRepository.save(producto);
    }

    @Override
    public Producto update(Producto producto, long id) {
        Producto productoBD = productosRepository.findById(id);
        if (productoBD != null) {
            productoBD.setNombre(producto.getNombre());
            productoBD.setDescripcion(producto.getDescripcion());
            productoBD.setImagen(producto.getImagen());
            productoBD.setPrecio(producto.getPrecio());
            productoBD.setUsoInterno(producto.getUsoInterno());
            productosRepository.save(productoBD);
        }
        return productoBD;
    }

    @Override
    public void delete(long id) {
        productosRepository.deleteById(id);
    }

    @Override
    public List<Producto> findAll() {
        return productosRepository.findAll();
    }

    @Override
    public Producto findById(long id) {
        return productosRepository.findById(id);
    }

    @Override
    public Producto findByNombre(String nombre) {
        return productosRepository.findByNombre(nombre);
    }
}
