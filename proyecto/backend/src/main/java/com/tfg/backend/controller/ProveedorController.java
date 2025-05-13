package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.ProveedorDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.ProveedorService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/proveedores")
@Slf4j
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    // ADMIN GLOBAL – ver todos los proveedores
    @GetMapping("/admin")
    public List<ProveedorDto> getAllProveedores() {
        return ProveedorDto.from(proveedorService.findAll());
    }

    // ADMIN GLOBAL – ver proveedor por ID
    @GetMapping("/admin/{id}")
    public ResponseEntity<?> getByIdAdmin(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.findById(id);
        if (proveedor != null) {
            return ResponseEntity.ok(ProveedorDto.from(proveedor));
        }
        return ResponseEntity.status(404).body(ErrorDto.from("Proveedor no encontrado"));
    }

    // EMPRESA – listar proveedores propios
    @GetMapping("")
    public ResponseEntity<?> getProveedoresDeEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        List<Proveedor> lista = proveedorService.findByEmpresa(empresa);
        return ResponseEntity.ok(ProveedorDto.from(lista));
    }

    // EMPRESA – ver proveedor propio
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Proveedor proveedor = proveedorService.findById(id);

        if (proveedor == null || !proveedor.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes acceder a este proveedor"));
        }

        return ResponseEntity.ok(ProveedorDto.from(proveedor));
    }

    // EMPRESA – crear proveedor
    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody ProveedorDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());
        Empresa empresa = user.getEmpresa();

        if (empresa == null) {
            return ResponseEntity.status(403).body(ErrorDto.from("No perteneces a ninguna empresa"));
        }

        Proveedor nuevo = dto.to();
        nuevo.setEmpresa(empresa);
        nuevo = proveedorService.save(nuevo);

        ProveedorDto dtoResponse = new ProveedorDto();
        dtoResponse.setId(nuevo.getId());
        dtoResponse.setNombre(nuevo.getNombre());
        dtoResponse.setTelefono(nuevo.getTelefono());
        dtoResponse.setEmail(nuevo.getEmail());

        return ResponseEntity.ok(dtoResponse);
    }

    // EMPRESA – editar proveedor propio
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProveedorDto dto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Proveedor existente = proveedorService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (existente == null || !existente.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes modificar este proveedor"));
        }

        Proveedor actualizado = proveedorService.update(dto.to(), id);
        return ResponseEntity.ok(ProveedorDto.from(actualizado));
    }

    // EMPRESA – eliminar proveedor propio
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Proveedor proveedor = proveedorService.findById(id);
        User user = userService.findById(userDetails.getId());

        if (proveedor == null || !proveedor.getEmpresa().equals(user.getEmpresa())) {
            return ResponseEntity.status(403).body(ErrorDto.from("No puedes eliminar este proveedor"));
        }

        proveedorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
