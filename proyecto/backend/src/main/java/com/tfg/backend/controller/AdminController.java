package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.dto.EmpresaDto;
import com.tfg.backend.model.dto.UserDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmpresaService empresaService;

    // GET – Todos los usuarios
    @GetMapping("/usuarios")
    public ResponseEntity<?> getAllUsuarios() {
        return ResponseEntity.ok(UserDto.from(userService.findAll()));
    }

    // GET – Todas las empresas
    @GetMapping("/empresas")
    public ResponseEntity<?> getAllEmpresas() {
        return ResponseEntity.ok(EmpresaDto.from(empresaService.findAll()));
    }

    // GET – Usuarios registrados por mes (real)
    @GetMapping("/stats/usuarios-por-mes")
    public ResponseEntity<?> getUsuariosPorMes() {
        List<User> usuarios = userService.findAll();
        Map<String, Long> conteoPorMes = usuarios.stream()
                .filter(u -> u.getCreatedAt() != null)
                .collect(Collectors.groupingBy(user -> {
                    LocalDateTime fecha = LocalDateTime.ofInstant(user.getCreatedAt(), ZoneId.systemDefault());
                    return fecha.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
                }, Collectors.counting()));

        // Convertir a lista ordenada
        List<Map<String, Object>> resultado = conteoPorMes.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("mes", entry.getKey());
                    item.put("total", entry.getValue());
                    return item;
                })
                .sorted(Comparator.comparing(m -> (String) m.get("mes")))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultado);
    }

    // GET – Últimos errores desde app.log
    @GetMapping("/errores")
    public ResponseEntity<?> getErroresSistema() {
        List<Map<String, String>> errores = new ArrayList<>();
        String rutaLog = "logs/app.log";

        try (BufferedReader br = new BufferedReader(new FileReader(rutaLog))) {
            br.lines()
                    .filter(linea -> linea.contains("ERROR"))
                    .limit(50)
                    .forEach(linea -> {
                        Map<String, String> error = new HashMap<>();
                        String[] partes = linea.split("ERROR");
                        if (partes.length > 1) {
                            error.put("fecha", partes[0].trim());
                            error.put("detalle", partes[1].trim());
                        } else {
                            error.put("linea", linea);
                        }
                        errores.add(error);
                    });
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("No se pudo leer el archivo de logs");
        }

        return ResponseEntity.ok(errores);
    }

    // GET – Estado básico del sistema
    @GetMapping("/estado")
    public ResponseEntity<?> getEstadoSistema() {
        Map<String, String> estado = new LinkedHashMap<>();
        estado.put("backend", "OK");
        estado.put("database", "OK");
        estado.put("version", "1.0.0");
        estado.put("uptime", "No implementado aún"); // puedes medirlo si lo deseas

        return ResponseEntity.ok(estado);
    }

    // POST – Enviar correo a todos (a implementar más adelante)
    @PostMapping("/enviar-correo")
    public ResponseEntity<?> enviarCorreoGlobal(@RequestBody Map<String, String> cuerpo) {
        String asunto = cuerpo.get("asunto");
        String mensaje = cuerpo.get("mensaje");

        log.info("Simulación de envío de correo a todos los usuarios: [{}] {}", asunto, mensaje);
        // TODO: Integrar con servicio de envío real

        return ResponseEntity.ok("Correo enviado (simulado)");
    }

    // DELETE - borrar usuarios

}
