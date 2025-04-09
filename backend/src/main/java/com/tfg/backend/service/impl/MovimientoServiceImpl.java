package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.Movimiento;
import com.tfg.backend.model.repository.MovimientoRepository;
import com.tfg.backend.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoServiceImpl implements MovimientoService {
    @Autowired
    private MovimientoRepository movimientoRepository;


    @Override
    public Movimiento save(Movimiento movimiento) {
        return movimientoRepository.save(movimiento);
    }

    @Override
    public void delete(long id) {
        movimientoRepository.deleteById(id);
    }

    @Override
    public Movimiento findById(long id) {
        return movimientoRepository.findById(id);
    }

    @Override
    public List<Movimiento> findByProductoId(long productoId) {
        return movimientoRepository.findByProductoId(productoId);
    }

    @Override
    public List<Movimiento> findByUsuarioId(long usuarioId) {
        return movimientoRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Movimiento> findByEmpresaId(long empresaId) {
        return movimientoRepository.buscarPorEmpresa(empresaId);
    }
}
