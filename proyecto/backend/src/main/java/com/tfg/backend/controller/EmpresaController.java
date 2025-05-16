package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.service.UserService;
import com.tfg.backend.model.dto.EmpresaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/empresas")
@Slf4j
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // GET – Listar todas
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("")
    public List<EmpresaDto> listAll() {
        return EmpresaDto.from(empresaService.findAll());
    }

    // GET – Buscar por ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable long id) {
        Empresa empresa = empresaService.findById(id);
        if (empresa != null) {
            return ResponseEntity.ok(EmpresaDto.from(empresa));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
    }

    // GET – Empresa del usuario autenticado
    @GetMapping("/mi-empresa")
    public ResponseEntity<?> getEmpresaDelUsuario(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userService.findById(userDetails.getId());

        if (user.getEmpresa() != null) {
            return ResponseEntity.ok(EmpresaDto.from(user.getEmpresa()));
        } else {
            return ResponseEntity.noContent().build(); 
        }
    }

    // POST – Crear empresa
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody EmpresaDto empresaDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // Recuperar entidad User desde el ID del token
            User usuarioActual = userService.findById(userDetails.getId());

            System.out.println("Usuario autenticado: " + usuarioActual.getUsername());

            Empresa empresa = empresaDto.to();
            empresaService.save(empresa);

            System.out.println("Empresa guardada con ID: " + empresa.getId());

            userService.actualizarRolYEmpresa(usuarioActual, empresa);

            return ResponseEntity.status(HttpStatus.CREATED).body(EmpresaDto.from(empresa));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no guardada"));
        }
    }

    // PUT – Editar empresa
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EmpresaDto empresaDto) {
        Empresa empresaExistente = empresaService.findById(id);
        if (empresaExistente != null) {
            try {
                Empresa actualizada = empresaService.update(empresaDto.to(), id);
                return ResponseEntity.ok(EmpresaDto.from(actualizada));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(ErrorDto.from("Empresa no modificada"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
    }

    // DELETE – Eliminar empresa
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Empresa empresa = empresaService.findById(id);
        if (empresa != null) {
            try {
                empresaService.delete(id);
                return ResponseEntity.ok(EmpresaDto.from(empresa));
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(ErrorDto.from("Empresa no eliminada"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
    }

    // POST – Unirse a empresa (por clave)
    @PreAuthorize("hasAnyRole('ROLE_EMPLEADO', 'ROLE_USER', 'ROLE_ADMIN_EMPRESA')")
    @PostMapping("/{empresaId}/unirse")
    public ResponseEntity<?> unirseAEmpresa(
            @PathVariable Long empresaId,
            @RequestParam String clave,
            @AuthenticationPrincipal User usuarioActual) {

        Empresa empresa = empresaService.findById(empresaId);
        if (empresa != null && clave != null && clave.equals(empresa.getClaveAcceso())) {
            userService.agregarUsuarioAEmpresa(usuarioActual, empresa);
            return ResponseEntity.ok("Usuario unido a la empresa correctamente.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorDto.from("Clave incorrecta o empresa no encontrada."));
    }
}
