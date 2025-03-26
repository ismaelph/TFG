package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.entity.ProductoUsuario;
import com.tfg.backend.model.repository.ProductoUsuarioRepository;
import com.tfg.backend.service.ProductosUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoUsuarioServiceImpl implements ProductosUsuarioService {
    @Autowired
    private ProductoUsuarioRepository productoUsuarioRepository;


    @Override
    public ProductoUsuario save(ProductoUsuario producto) {
        return productoUsuarioRepository.save(producto);
    }

    @Override
    public ProductoUsuario update(ProductoUsuario producto, long id) {
        ProductoUsuario puBD = productoUsuarioRepository.findById(id);
        if (puBD != null) {
            puBD.setProducto(producto.getProducto());
            puBD.setUsuario(producto.getUsuario());
            puBD.setFechaAsignacion(producto.getFechaAsignacion());
            productoUsuarioRepository.save(puBD);
        }
        return puBD;
    }

    @Override
    public void delete(long id) {
        productoUsuarioRepository.deleteById(id);
    }

    @Override
    public List<ProductoUsuario> findAll() {
        return productoUsuarioRepository.findAll();
    }

    @Override
    public ProductoUsuario findById(long id) {
        return productoUsuarioRepository.findById(id);
    }

    @Override
    public ProductoUsuario findByProducto(Producto producto) {
        return productoUsuarioRepository.findByProducto(producto.getId());
    }

    @Override
    public ProductoUsuario findByUsuario(User usuario) {
        return productoUsuarioRepository.findByUsuario(usuario.getId());
    }
}
