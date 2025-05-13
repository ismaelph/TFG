package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.service.ProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto update(Producto productoEditado, Long id) {
        Producto productoBD = findById(id);

        if (productoEditado.getNombre() != null)
            productoBD.setNombre(productoEditado.getNombre());

        if (productoEditado.getPrecio() != null)
            productoBD.setPrecio(productoEditado.getPrecio());

        if (productoEditado.getCantidad() != null)
            productoBD.setCantidad(productoEditado.getCantidad());

        productoBD.setUsoInterno(productoEditado.isUsoInterno());

        if (productoEditado.getCategoria() != null)
            productoBD.setCategoria(productoEditado.getCategoria());

        if (productoEditado.getProveedor() != null)
            productoBD.setProveedor(productoEditado.getProveedor());

        return productoRepository.save(productoBD);
    }

    @Override
    public void delete(Long id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public List<Producto> findByEmpresa(Empresa empresa) {
        return productoRepository.findByEmpresa(empresa);
    }
}
