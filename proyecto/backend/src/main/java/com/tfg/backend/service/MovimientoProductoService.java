package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.entity.Producto;

import java.util.List;

public interface MovimientoProductoService {
    MovimientoProducto save(MovimientoProducto movimiento);
    MovimientoProducto findById(Long id);
    List<MovimientoProducto> findByEmpresa(Empresa empresa);
    List<MovimientoProducto> findByProducto(Producto producto);
    List<MovimientoProducto> findByUsuario(User usuario);
    List<MovimientoProducto> findAll();
    List<MovimientoProducto> findByUsuarioId(Long userId);
    void reducirCantidadInventarioPersonal(Long userId, Long productoId, int cantidadAReducir);
    void entregarProductoAlUsuario(Long productoId, Long usuarioId, int cantidad);

}
