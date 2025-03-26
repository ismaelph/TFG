package com.tfg.backend.service;

import com.tfg.backend.model.entity.MovimientoInventario;

import java.util.List;

public interface MovimientosInventarioService {
    MovimientoInventario save(MovimientoInventario movimientoInventario);
    MovimientoInventario update(MovimientoInventario movimientoInventario, long id);
    void delete(long id);

    List<MovimientoInventario> findAll();
    MovimientoInventario findById(long id);
    List<MovimientoInventario> findByTipoMovimiento (String tipoMovimiento);
}
