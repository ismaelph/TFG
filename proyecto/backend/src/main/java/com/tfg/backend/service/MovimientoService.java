package com.tfg.backend.service;

import com.tfg.backend.model.entity.Movimiento;

import java.util.List;

public interface MovimientoService {
    Movimiento save(Movimiento movimiento);
    void delete(long id);

    Movimiento findById(long id);
    List<Movimiento> findByProductoId(long productoId);
    List<Movimiento> findByUsuarioId(long usuarioId);
    List<Movimiento> findByEmpresaId(long empresaId);
}
