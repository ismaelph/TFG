package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.*;
import com.tfg.backend.model.entity.*;
import com.tfg.backend.model.enums.TipoMovimiento;
import com.tfg.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/productos")
@Slf4j
public class ProductoController {

    @Autowired private ProductoService productoService;
    @Autowired private EmpresaService empresaService;
    @Autowired private CategoriaService categoriaService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private UserService userService;
    @Autowired private MovimientoProductoService movimientoService;

    // LISTAR productos de mi empresa
    @GetMapping("")
    public ResponseEntity<?> listByEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        List<Producto> productos = productoService.findByEmpresa(empresa);
        return ResponseEntity.ok(ProductoDto.from(productos));
    }

    // OBTENER producto por ID (solo si es de mi empresa)
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Producto producto = productoService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (producto == null || !producto.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(404).body(ErrorDto.from("Producto no encontrado o acceso denegado"));
        }

        return ResponseEntity.ok(ProductoDto.from(producto));
    }

    // CREAR producto
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProductoDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        Producto nuevo = dto.to();
        nuevo.setEmpresa(empresa);
        nuevo.setCategoria(categoriaService.findById(dto.getCategoriaId()));
        nuevo.setProveedor(dto.getProveedorId() != null ? proveedorService.findById(dto.getProveedorId()) : null);
        nuevo.setUsuario(user);

        Producto guardado = productoService.save(nuevo);
        return ResponseEntity.ok(ProductoDto.from(guardado));
    }

    // EDITAR producto
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductoDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Producto existente = productoService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (existente == null || !existente.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes modificar este producto"));
        }

        Producto actualizado = dto.to();
        actualizado.setCategoria(categoriaService.findById(dto.getCategoriaId()));
        actualizado.setProveedor(dto.getProveedorId() != null ? proveedorService.findById(dto.getProveedorId()) : null);

        Producto finalizado = productoService.update(actualizado, id);
        return ResponseEntity.ok(ProductoDto.from(finalizado));
    }

    // ELIMINAR producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Producto producto = productoService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (producto == null || !producto.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes eliminar este producto"));
        }

        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Inventario personal del usuario autenticado
    @GetMapping("/mi-inventario")
    public ResponseEntity<?> getInventarioDelUsuario(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        List<MovimientoProducto> movimientos = movimientoService.findByUsuarioId(user.getId());

        System.out.println("🔍 Movimientos encontrados para el usuario " + user.getUsername() + ": " + movimientos.size());

        Map<Long, InventarioPersonalDto> inventario = new HashMap<>();

        for (MovimientoProducto mov : movimientos) {
            Producto producto = mov.getProducto();
            if (producto == null) {
                System.out.println("⚠️ Movimiento con producto NULL, ID: " + mov.getId());
                continue;
            }

            String tipo = mov.getTipo() != null ? mov.getTipo().toString() : "NULL";
            int cantidad = mov.getCantidad();

            System.out.println("✅ Movimiento → Producto: " + producto.getNombre()
                    + " | Tipo: " + tipo
                    + " | Cantidad: " + cantidad);

            Long id = producto.getId();
            String nombre = producto.getNombre();
            String categoria = producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin categoría";
            String proveedor = producto.getProveedor() != null ? producto.getProveedor().getNombre() : "Sin proveedor";
            int ajuste = cantidad * ("ENTRADA".equals(tipo) ? 1 : -1);

            inventario.compute(id, (k, v) -> {
                if (v == null) return new InventarioPersonalDto(id, nombre, ajuste, categoria, proveedor);
                v.setCantidad(v.getCantidad() + ajuste);
                return v;
            });
        }

        List<InventarioPersonalDto> resultado = inventario.values().stream()
                .filter(i -> i.getCantidad() > 0)
                .toList();

        System.out.println("📦 Inventario generado con " + resultado.size() + " productos únicos.");

        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferirProducto(@RequestBody MovimientoProductoDto dto,
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

        if (dto.getCantidad() <= 0 || producto.getCantidad() < dto.getCantidad()) {
            return ResponseEntity.status(400).body(ErrorDto.from("Cantidad inválida o sin stock"));
        }

        // 1. Movimiento SALIDA (empresa)
        MovimientoProducto salida = new MovimientoProducto();
        salida.setProducto(producto);
        salida.setEmpresa(empresa);
        salida.setUsuario(user);
        salida.setTipo(TipoMovimiento.SALIDA);
        salida.setCantidad(dto.getCantidad());
        salida.setFecha(LocalDateTime.now());
        movimientoService.save(salida);

        // Actualizar stock empresa
        producto.setCantidad(producto.getCantidad() - dto.getCantidad());
        productoService.save(producto);

        // 2. Movimiento ENTRADA (usuario personal)
        MovimientoProducto entrada = new MovimientoProducto();
        entrada.setProducto(producto);
        entrada.setEmpresa(empresa);
        entrada.setUsuario(user);
        entrada.setTipo(TipoMovimiento.ENTRADA);
        entrada.setCantidad(dto.getCantidad());
        entrada.setFecha(LocalDateTime.now());
        movimientoService.save(entrada);

        return ResponseEntity.ok("Transferencia completada");
    }



}
