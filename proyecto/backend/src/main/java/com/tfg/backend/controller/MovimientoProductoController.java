package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.MovimientoProductoDto;
import com.tfg.backend.model.entity.*;
import com.tfg.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/movimientos")
@Slf4j
public class MovimientoProductoController {

    @Autowired private MovimientoProductoService movimientoService;
    @Autowired private ProductoService productoService;
    @Autowired private UserService userService;
    @Autowired private EmpresaService empresaService;

    @PostMapping("")
    public ResponseEntity<?> registrar(@RequestBody MovimientoProductoDto dto,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        Producto producto = productoService.findById(dto.getProductoId());
        if (producto == null || !producto.getEmpresa().equals(empresa)) {
            return ResponseEntity.status(403).body(ErrorDto.from("No tienes acceso a este producto"));
        }

        // Validar stock
        int actual = producto.getCantidad();
        if (dto.getTipo().toString().equals("SALIDA")) {
            if (dto.getCantidad() > actual) {
                return ResponseEntity.status(400).body(ErrorDto.from("No hay suficiente stock"));
            }
            producto.setCantidad(actual - dto.getCantidad());
        } else {
            producto.setCantidad(actual + dto.getCantidad());
        }
        productoService.save(producto);

        MovimientoProducto movimiento = dto.to();
        movimiento.setEmpresa(empresa);
        movimiento.setUsuario(user);
        movimiento.setProducto(producto);

        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.save(movimiento)));
    }

    @GetMapping("/mios")
    public ResponseEntity<?> misMovimientos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.findByUsuario(user)));
    }

    @GetMapping("")
    public ResponseEntity<?> movimientosDeEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.findByEmpresa(empresa)));
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<?> movimientosPorProducto(@PathVariable Long id,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Producto producto = productoService.findById(id);

        if (producto == null || !producto.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No tienes acceso a este producto"));
        }

        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.findByProducto(producto)));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> todos() {
        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.findAll()));
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<?> todoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(MovimientoProductoDto.from(movimientoService.findById(id)));
    }
}
