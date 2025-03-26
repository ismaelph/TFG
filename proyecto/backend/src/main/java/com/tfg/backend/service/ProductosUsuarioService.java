package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.entity.ProductoUsuario;

import java.util.List;

public interface ProductosUsuarioService {
    ProductoUsuario save(ProductoUsuario producto);
    ProductoUsuario update(ProductoUsuario producto, long id);
    void delete(long id);

    List<ProductoUsuario> findAll();
    ProductoUsuario findById(long id);
    ProductoUsuario findByProducto(Producto producto);
    ProductoUsuario findByUsuario(User usuario);
}
