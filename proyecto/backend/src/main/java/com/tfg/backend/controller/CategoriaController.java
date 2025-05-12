package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.CategoriaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.CategoriaService;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categorias")
@Slf4j
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    // ✅ ADMIN GLOBAL: ver todas las categorías
    @GetMapping("/admin")
    public List<CategoriaDto> listAll() {
        return CategoriaDto.from(categoriaService.findAll());
    }

    // ✅ ADMIN GLOBAL: obtener cualquier categoría por ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getByIdAdmin(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        if (categoria != null) {
            return ResponseEntity.ok(CategoriaDto.from(categoria));
        }
        return ResponseEntity.status(404).body(ErrorDto.from("Categoría no encontrada"));
    }

    // ✅ EMPRESA: listar categorías de mi empresa
    @GetMapping("")
    public ResponseEntity<?> listByEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        List<Categoria> categorias = categoriaService.findByEmpresa(empresa);
        return ResponseEntity.ok(CategoriaDto.from(categorias));
    }

    // ✅ EMPRESA: ver una categoría si es mía
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Categoria categoria = categoriaService.findById(id);

        if (categoria == null || !categoria.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(404).body(ErrorDto.from("Categoría no encontrada o acceso denegado"));
        }

        return ResponseEntity.ok(CategoriaDto.from(categoria));
    }

    // ✅ EMPRESA: crear nueva categoría (solo si tienes empresa)
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody CategoriaDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        Categoria nueva = dto.to();
        nueva.setEmpresa(empresa);
        nueva = categoriaService.save(nueva);

        CategoriaDto dtoResponse = new CategoriaDto();
        dtoResponse.setId(nueva.getId());
        dtoResponse.setNombre(nueva.getNombre());

        return ResponseEntity.ok(dtoResponse);
    }

    // ✅ EMPRESA: editar categoría si es tuya
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CategoriaDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Categoria existente = categoriaService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (existente == null || !existente.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No tienes permiso para modificar esta categoría"));
        }

        Categoria actualizada = categoriaService.update(dto.to(), id);
        return ResponseEntity.ok(CategoriaDto.from(actualizada));
    }

    // ✅ EMPRESA: eliminar categoría si es tuya
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Categoria categoria = categoriaService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (categoria == null || !categoria.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No tienes permiso para eliminar esta categoría"));
        }

        categoriaService.delete(id);
        return ResponseEntity.ok("Categoría eliminada correctamente.");
    }
}
