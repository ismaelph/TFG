package com.tfg.backend.controller;

import com.tfg.backend.auth.models.Role;
import com.tfg.backend.auth.models.RoleEnum;
import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.RoleRepository;
import com.tfg.backend.model.dto.CambiarPasswordDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.UserDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;



    // GET – listar todos
    @GetMapping("")
    public List<UserDto> listAll() {
        return UserDto.from(userService.findAll());
    }

    // GET – buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        User user = userService.findById(id);
        if (user != null) {
            return ResponseEntity.ok(UserDto.from(user));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorDto.from("Usuario no encontrado"));
        }
    }

    // PUT – editar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto) {
        User userBD = userService.findById(id);
        if (userBD != null) {
            try {
                User updated = userService.update(userDto.to(), id);
                return ResponseEntity.ok(UserDto.from(updated));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ErrorDto.from("Usuario no modificado"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.from("Usuario no encontrado"));
    }

    // DELETE – eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        User user = userService.findById(id);
        if (user != null) {
            try {
                userService.delete(id);
                return ResponseEntity.ok("Usuario eliminado correctamente.");
            } catch (Exception e) {
                e.printStackTrace(); // útil para ver el error exacto en consola
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ErrorDto.from("Error interno al eliminar usuario"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.from("Usuario no encontrado"));
    }


    // POST – unir usuario a empresa
    @PostMapping("/{userId}/empresa/{empresaId}")
    public ResponseEntity<?> unirUsuarioAEmpresa(@PathVariable long userId, @PathVariable long empresaId) {
        User user = userService.findById(userId);
        Empresa empresa = empresaService.findById(empresaId);

        if (user != null && empresa != null) {
            userService.agregarUsuarioAEmpresa(user, empresa);
            return ResponseEntity.ok("Usuario unido a la empresa correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.from("Usuario o empresa no encontrados"));
    }

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@PathVariable long id, @RequestBody CambiarPasswordDto dto) {
        User user = userService.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorDto.from("Usuario no encontrado"));
        }

        // Verifica la contraseña actual
        if (!passwordEncoder.matches(dto.getPasswordActual(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ErrorDto.from("La contraseña actual es incorrecta"));
        }

        // Actualiza la contraseña
        user.setPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        userService.save(user);

        return ResponseEntity.ok("Contraseña actualizada correctamente.");
    }

    // PUT para expulsar usuarios de la empresa
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    @PutMapping("/{id}/expulsar")
    public ResponseEntity<?> expulsarUsuario(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Usuario no encontrado"));
        }

        if (user.getEmpresa() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Este usuario no pertenece a ninguna empresa"));
        }

        // Quitar empresa y dejar solo rol ROLE_USER
        user.setEmpresa(null);
        Role roleUser = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Rol ROLE_USER no encontrado"));

        user.setRoles(Set.of(roleUser));
        userService.save(user);

        return ResponseEntity.ok().body("Usuario expulsado correctamente.");
    }



}
