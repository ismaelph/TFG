package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.UserDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/usuarios")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

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

    // POST – crear usuario
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        try {
            User user = userDto.to();
            userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(UserDto.from(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ErrorDto.from("Usuario no guardado"));
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
                return ResponseEntity.ok(UserDto.from(user));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ErrorDto.from("Usuario no eliminado"));
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
}
