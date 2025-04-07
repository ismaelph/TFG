package com.tfg.backend.service;

import com.tfg.backend.model.entity.Movimiento;

import java.util.List;

public interface MovimientoService {
    Movimiento save(Movimiento movimiento);
    void delete(Long id);

    Movimiento findById(Long id);
    List<Movimiento> findByProductoId(Long productoId);
    List<Movimiento> findByUsuarioId(Long usuarioId);
    List<Movimiento> findByEmpresaId(Long empresaId);
}
