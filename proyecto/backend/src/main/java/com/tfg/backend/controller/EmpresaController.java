package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserService;
import com.tfg.backend.model.dto.EmpresaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/empresas")
@Slf4j
@Controller
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({""})
    public List<EmpresaDto> listAll() {
        return EmpresaDto.from(empresaService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byNombre(@PathVariable String nombre) {
        Empresa empresaBd = empresaService.findByNombre(nombre);
        if (empresaBd != null) {
            return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBd));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no encontrada"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody EmpresaDto empresaDto) {
        try {
            Empresa empresaBd = empresaDto.to();
            empresaService.save(empresaBd);
            return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no guardada"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Empresa empresaBd = empresaService.findById(id);
        if (empresaBd != null) {
            try {
                empresaService.delete(empresaBd.getId());
                return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBd));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EmpresaDto empresaDto) {
        Empresa empresaBd = empresaService.findById(id);
        if (empresaBd != null) {
            try {
                empresaBd = empresaService.update(empresaDto.to(), empresaBd.getId());
                return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBd));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no modificada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
        }
    }

    @PostMapping("/{empresaId}/unirse")
    public ResponseEntity<?> unirseAEmpresa(@PathVariable Long empresaId, @RequestParam String password, @AuthenticationPrincipal User usuarioActual) {
        Empresa empresa = empresaService.findById(empresaId);
        if (empresa != null && passwordEncoder.matches(password, empresa.getPassword())) {
            userService.agregarUsuarioAEmpresa(usuarioActual, empresa);
            return ResponseEntity.ok("Usuario añadido a la empresa.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta.");
    }


}
