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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired private EstanteriaService estanteriaService;

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
    public ResponseEntity<?> create(
            @RequestPart("producto") ProductoDto dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        // Guardar imagen en carpeta de empresa
        String imagenUrl = null;
        if (imagen != null && !imagen.isEmpty()) {
            try {
                String rutaBase = "uploads/productos/" + empresa.getNombre();
                File carpeta = new File(rutaBase);
                if (!carpeta.exists()) carpeta.mkdirs();

                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                Path rutaImagen = Paths.get(rutaBase, nombreArchivo);
                Files.write(rutaImagen, imagen.getBytes());

                imagenUrl = "/productos/" + empresa.getNombre() + "/" + nombreArchivo;
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Error al guardar la imagen.");
            }
        }

        Producto nuevo = dto.to();
        nuevo.setImagenUrl(imagenUrl);
        nuevo.setEmpresa(empresa);
        nuevo.setCategoria(categoriaService.findById(dto.getCategoriaId()));
        nuevo.setProveedor(dto.getProveedorId() != null ? proveedorService.findById(dto.getProveedorId()) : null);
        nuevo.setUsuario(user);

        Producto guardado = productoService.save(nuevo);
        return ResponseEntity.ok(ProductoDto.from(guardado));
    }


    // EDITAR producto
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestPart("producto") ProductoDto dto,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        System.out.println("📡 [PUT] /api/productos/" + id + " | EDITAR PRODUCTO");

        Producto existente = productoService.findById(id);
        User usuario = userService.findById(userDetails.getId());

        System.out.println("🟢 Producto ID: " + (existente != null ? existente.getId() : "null"));
        System.out.println("🏢 Empresa del producto: " + (existente != null && existente.getEmpresa() != null ? existente.getEmpresa().getNombre() : "null"));
        System.out.println("👤 Usuario autenticado: " + usuario.getUsername());
        System.out.println("🏢 Empresa del usuario: " + (usuario.getEmpresa() != null ? usuario.getEmpresa().getNombre() : "null"));

        if (existente == null) {
            System.err.println("❌ Producto no encontrado con ID: " + id);
            return ResponseEntity.status(404).body(ErrorDto.from("Producto no encontrado"));
        }

        if (usuario.getEmpresa() == null || !existente.getEmpresa().getId().equals(usuario.getEmpresa().getId())) {
            System.err.println("🚫 El usuario no pertenece a la empresa del producto. Acceso denegado.");
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes modificar este producto"));
        }

        String imagenUrl = existente.getImagenUrl(); // mantener imagen actual si no se cambia

        if (imagen != null && !imagen.isEmpty()) {
            try {
                System.out.println("📎 Nueva imagen recibida: " + imagen.getOriginalFilename());

                String rutaBase = "uploads/productos/" + existente.getEmpresa().getNombre();
                File carpeta = new File(rutaBase);
                if (!carpeta.exists()) {
                    boolean creada = carpeta.mkdirs();
                    System.out.println("📁 Carpeta creada: " + rutaBase + " → " + creada);
                }

                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                Path rutaImagen = Paths.get(rutaBase, nombreArchivo);
                Files.write(rutaImagen, imagen.getBytes());

                imagenUrl = "/productos/" + existente.getEmpresa().getNombre() + "/" + nombreArchivo;
                System.out.println("✅ Imagen guardada: " + imagenUrl);

            } catch (IOException e) {
                System.err.println("❌ Error al guardar la imagen:");
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error al guardar la imagen.");
            }
        } else {
            System.out.println("📂 No se recibió imagen nueva. Se mantiene la existente.");
        }

        // Construir el producto actualizado
        Producto actualizado = dto.to();
        actualizado.setImagenUrl(imagenUrl);
        actualizado.setCategoria(categoriaService.findById(dto.getCategoriaId()));
        actualizado.setProveedor(dto.getProveedorId() != null ? proveedorService.findById(dto.getProveedorId()) : null);
        actualizado.setEstanteria(estanteriaService.findById(dto.getEstanteriaId())); // ✅ Cargar completa la estantería

        productoService.update(actualizado, id);
        Producto finalizado = productoService.findById(id);

        // 🔁 Recargar estantería con ubicación para evitar null
        if (finalizado.getEstanteria() != null) {
            finalizado.setEstanteria(estanteriaService.findById(finalizado.getEstanteria().getId()));
            System.out.println("🔁 Estantería recargada: ID = " + finalizado.getEstanteria().getId());
        }

        System.out.println("✅ Producto actualizado correctamente. ID: " + finalizado.getId() + ", Nombre: " + finalizado.getNombre());
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
