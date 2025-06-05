package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.model.enums.TipoMovimiento;
import com.tfg.backend.model.repository.MovimientoProductoRepository;
import com.tfg.backend.model.repository.ProductoRepository;
import com.tfg.backend.service.MovimientoProductoService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
public class MovimientoProductoServiceImpl implements MovimientoProductoService {

    @Autowired
    private MovimientoProductoRepository movimientoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductoRepository productoRepository;

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
        if (empresa == null) {
            System.err.println("❌ Error: Empresa nula al intentar buscar movimientos.");
            throw new IllegalArgumentException("Empresa no puede ser nula para esta operación.");
        }
        System.out.println("📦 Obteniendo movimientos para la empresa: " + empresa.getNombre());
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

    @Override
    public void reducirCantidadInventarioPersonal(Long userId, Long productoId, int cantidadAReducir) {
        if (cantidadAReducir <= 0) {
            throw new IllegalArgumentException("La cantidad a reducir debe ser mayor que 0.");
        }

        // Obtener todos los movimientos del usuario para este producto
        List<MovimientoProducto> entradas = movimientoRepository
                .findByUsuarioIdAndProductoIdAndTipoOrderByFechaAsc(userId, productoId, TipoMovimiento.ENTRADA);

        List<MovimientoProducto> salidas = movimientoRepository
                .findByUsuarioIdAndProductoIdAndTipo(userId, productoId, TipoMovimiento.SALIDA);

        int totalEntradas = entradas.stream().mapToInt(MovimientoProducto::getCantidad).sum();
        int totalSalidas = salidas.stream().mapToInt(MovimientoProducto::getCantidad).sum();
        int cantidadDisponible = totalEntradas - totalSalidas;

        System.out.println("🔍 Entradas encontradas: " + entradas.size());
        System.out.println("📤 Salidas encontradas: " + salidas.size());
        System.out.println("📦 Disponible real: " + cantidadDisponible + " | Solicitado a reducir: " + cantidadAReducir);

        if (cantidadDisponible < cantidadAReducir) {
            throw new IllegalArgumentException("No tienes suficiente cantidad de este producto para eliminar.");
        }

        // Registrar movimiento de salida para dejar trazabilidad
        MovimientoProducto salida = new MovimientoProducto();
        salida.setUsuario(userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        salida.setProducto(productoRepository.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado")));
        salida.setCantidad(cantidadAReducir);
        salida.setTipo(TipoMovimiento.SALIDA);
        salida.setFecha(LocalDateTime.now());
        movimientoRepository.save(salida);

        System.out.println("✅ Movimiento de salida registrado correctamente. Producto ID: " + productoId + ", cantidad: " + cantidadAReducir);
    }


}
