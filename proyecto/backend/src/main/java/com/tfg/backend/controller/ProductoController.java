package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ProductoDto;
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
@RequestMapping("/api/productos")
@Slf4j
public class ProductoController {

    @Autowired private ProductoService productoService;
    @Autowired private EmpresaService empresaService;
    @Autowired private CategoriaService categoriaService;
    @Autowired private ProveedorService proveedorService;
    @Autowired private UserService userService;

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

    // Obtener todos los productos (ADMIN)
    @GetMapping("/admin")
    public ResponseEntity<?> listAll() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(ProductoDto.from(productos));
    }

    // Obtener todos los productos por id (ADMIN)
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getByIdAdmin(@PathVariable Long id) {
        Producto producto = productoService.findById(id);
        if (producto == null) {
            return ResponseEntity.status(404).body(ErrorDto.from("Producto no encontrado"));
        }
        return ResponseEntity.ok(ProductoDto.from(producto));
    }


    // CREAR nuevo producto
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

    // EDITAR producto si es de tu empresa
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

    // ELIMINAR producto si es de tu empresa
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
}
