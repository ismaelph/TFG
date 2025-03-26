package com.tfg.backend.service.impl;

import com.tfg.backend.model.entity.MovimientoInventario;
import com.tfg.backend.model.repository.MovimientosInventarioRepository;
import com.tfg.backend.service.MovimientosInventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoInventarioServiceImpl implements MovimientosInventarioService {
    @Autowired
    private MovimientosInventarioRepository movimientosInventarioRepository;


    @Override
    public MovimientoInventario save(MovimientoInventario movimientoInventario) {
        return movimientosInventarioRepository.save(movimientoInventario);
    }

    @Override
    public MovimientoInventario update(MovimientoInventario movimientoInventario, long id) {
        MovimientoInventario mviBD = movimientosInventarioRepository.findById(id);
        if (mviBD != null) {
            mviBD.setProducto(movimientoInventario.getProducto());
            mviBD.setUsuario(movimientoInventario.getUsuario());
            mviBD.setFecha(movimientoInventario.getFecha());
            mviBD.setCantidad(movimientoInventario.getCantidad());
            mviBD.setDescripcion(movimientoInventario.getDescripcion());
            mviBD.setFecha(movimientoInventario.getFecha());
            movimientosInventarioRepository.save(mviBD);
        }
        return mviBD;
    }

    @Override
    public void delete(long id) {
        movimientosInventarioRepository.deleteById(id);
    }

    @Override
    public List<MovimientoInventario> findAll() {
        return movimientosInventarioRepository.findAll();
    }

    @Override
    public MovimientoInventario findById(long id) {
        return movimientosInventarioRepository.findById(id);
    }

    @Override
    public List<MovimientoInventario> findByTipoMovimiento(String tipoMovimiento) {
        return movimientosInventarioRepository.findByTipoMovimiento(tipoMovimiento);
    }
}
