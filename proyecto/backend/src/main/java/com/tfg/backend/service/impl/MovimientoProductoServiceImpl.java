package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.repository.MovimientoProductoRepository;
import com.tfg.backend.service.MovimientoProductoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MovimientoProductoServiceImpl implements MovimientoProductoService {

    @Autowired
    private MovimientoProductoRepository movimientoRepository;

    @Override
    public MovimientoProducto save(MovimientoProducto movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public MovimientoProducto findById(Long id) {
        return movimientoRepository.findById(id).orElse(null);
    }

    @Override
    public List<MovimientoProducto> findByEmpresa(Empresa empresa) {
        if (empresa == null) return movimientoRepository.findAll();
        return movimientoRepository.findByEmpresa(empresa);
    }

    @Override
    public List<MovimientoProducto> findByProducto(Producto producto) {
        return movimientoRepository.findByProducto(producto);
    }

    @Override
    public List<MovimientoProducto> findByUsuario(User usuario) {
        return movimientoRepository.findByUsuario(usuario);
    }

    @Override
    public List<MovimientoProducto> findAll() {
        return movimientoRepository.findAll();
    }

    @Override
    public List<MovimientoProducto> findByUsuarioId(Long userId) {
        return movimientoRepository.findByUsuarioId(userId);
    }

}
