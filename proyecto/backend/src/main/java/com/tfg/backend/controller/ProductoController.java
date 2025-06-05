package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.payload.response.MessageResponse;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.*;
import com.tfg.backend.model.entity.*;
import com.tfg.backend.model.enums.TipoMovimiento;
import com.tfg.backend.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
                // 🔧 Normaliza nombre de empresa: quita espacios, tildes, ñ y símbolos raros
                String carpetaEmpresa = empresa.getNombre()
                        .toLowerCase()
                        .replaceAll("[áàäâÁÀÄÂ]", "a")
                        .replaceAll("[éèëêÉÈËÊ]", "e")
                        .replaceAll("[íìïîÍÌÏÎ]", "i")
                        .replaceAll("[óòöôÓÒÖÔ]", "o")
                        .replaceAll("[úùüûÚÙÜÛ]", "u")
                        .replaceAll("ñ", "n")
                        .replaceAll("[^a-z0-9]", "_");

                // 📂 Crear carpeta si no existe
                String rutaBase = "uploads/productos/" + carpetaEmpresa;
                File carpeta = new File(rutaBase);
                if (!carpeta.exists()) carpeta.mkdirs();

                // 🖼️ Guardar archivo
                String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                Path rutaImagen = Paths.get(rutaBase, nombreArchivo);
                Files.write(rutaImagen, imagen.getBytes());

                // 🌐 Ruta accesible desde el frontend
                imagenUrl = "/uploads/productos/" + carpetaEmpresa + "/" + nombreArchivo;

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

                imagenUrl = "uploads/productos/" + existente.getEmpresa().getNombre() + "/" + nombreArchivo;
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

    @GetMapping("/mi-inventario")
    @PreAuthorize("hasRole('ROLE_EMPLEADO')")
    public ResponseEntity<?> getInventarioDelUsuario(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());

        List<MovimientoProducto> movimientos = movimientoService.findByUsuarioId(user.getId());

        // Map para calcular stock real por producto
        Map<Long, Integer> stockMap = new HashMap<>();
        Map<Long, MovimientoProducto> productoRef = new HashMap<>();

        for (MovimientoProducto mov : movimientos) {
            Long prodId = mov.getProducto().getId();
            int cantidad = mov.getTipo() == TipoMovimiento.ENTRADA ? mov.getCantidad() : -mov.getCantidad();

            stockMap.put(prodId, stockMap.getOrDefault(prodId, 0) + cantidad);
            productoRef.putIfAbsent(prodId, mov); // Guarda referencia a un movimiento para extraer el producto
        }

        // Filtrar productos con stock > 0
        List<InventarioPersonalDto> resultado = stockMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    MovimientoProducto mov = productoRef.get(entry.getKey());
                    Producto producto = mov.getProducto();

                    // 🔧 Forzar carga completa de la estantería
                    if (producto.getEstanteria() != null) {
                        producto.setEstanteria(estanteriaService.findById(producto.getEstanteria().getId()));
                    }

                    String categoria = producto.getCategoria() != null ? producto.getCategoria().getNombre() : "Sin categoría";
                    String proveedor = producto.getProveedor() != null ? producto.getProveedor().getNombre() : "Sin proveedor";
                    String imagenUrl = producto.getImagenUrl() != null ? producto.getImagenUrl() : "";

                    String ubicacion = "No asignada";
                    if (producto.getEstanteria() != null &&
                            producto.getEstanteria().getPlanta() != null &&
                            producto.getEstanteria().getPlanta().getAlmacen() != null) {
                        String almacen = producto.getEstanteria().getPlanta().getAlmacen().getNombre();
                        int planta = producto.getEstanteria().getPlanta().getNumero();
                        String est = producto.getEstanteria().getCodigo();
                        ubicacion = almacen + " - Planta " + planta + " - " + est;
                    }

                    System.out.println(ubicacion);

                    return new InventarioPersonalDto(
                            producto.getId(),
                            producto.getNombre(),
                            entry.getValue(),
                            categoria,
                            proveedor,
                            imagenUrl,
                            ubicacion
                    );
                })
                .toList();

        return ResponseEntity.ok(resultado);
    }



    @PostMapping("/transferir")
    public ResponseEntity<?> transferirProducto(@RequestBody MovimientoProductoDto dto,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        System.out.println("📡 [POST] /api/productos/transferir");

        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        System.out.println("👤 Usuario: " + user.getUsername());
        System.out.println("🏢 Empresa: " + (empresa != null ? empresa.getNombre() : "null"));

        if (empresa == null) {
            System.err.println("🚫 Usuario sin empresa.");
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        Producto producto = productoService.findById(dto.getProductoId());

        if (producto == null) {
            System.err.println("❌ Producto no encontrado. ID: " + dto.getProductoId());
            return ResponseEntity.status(404).body(ErrorDto.from("Producto no encontrado"));
        }

        if (!producto.getEmpresa().equals(empresa)) {
            System.err.println("🚫 Producto no pertenece a la empresa del usuario.");
            return ResponseEntity.status(403).body(ErrorDto.from("No tienes acceso a este producto"));
        }

        if (dto.getCantidad() <= 0) {
            System.err.println("⚠️ Cantidad inválida: " + dto.getCantidad());
            return ResponseEntity.status(400).body(ErrorDto.from("La cantidad debe ser mayor que 0"));
        }

        if (producto.getCantidad() < dto.getCantidad()) {
            System.err.println("⚠️ Stock insuficiente. Disponible: " + producto.getCantidad() + ", Solicitado: " + dto.getCantidad());
            return ResponseEntity.status(400).body(ErrorDto.from("No hay suficiente stock disponible"));
        }

        try {
            // ✅ Solo registramos ENTRADA al usuario y actualizamos stock (nueva lógica)
            movimientoService.entregarProductoAlUsuario(dto.getProductoId(), user.getId(), dto.getCantidad());
            System.out.println("✅ Transferencia realizada con éxito");
            return ResponseEntity.ok(new MessageResponse("Transferencia completada"));
        } catch (Exception e) {
            System.err.println("❌ Error al transferir producto: " + e.getMessage());
            return ResponseEntity.status(500).body(ErrorDto.from("Error al transferir producto: " + e.getMessage()));
        }
    }

    @PutMapping("/mi-inventario/{productoId}/reducir")
    public ResponseEntity<?> reducirCantidadPersonal(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long productoId,
            @RequestParam int cantidad) {

        try {
            movimientoService.reducirCantidadInventarioPersonal(userDetails.getId(), productoId, cantidad);
            return ResponseEntity.ok(new MessageResponse("Cantidad reducida correctamente en tu inventario."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }


}
