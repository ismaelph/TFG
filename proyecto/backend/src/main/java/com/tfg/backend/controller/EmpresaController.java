package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.CambiarClaveEmpresaDto;
import com.tfg.backend.model.repository.CategoriaRepository;
import com.tfg.backend.model.repository.ProveedorRepository;
import com.tfg.backend.model.repository.UsuarioRepository;
import com.tfg.backend.service.CorreoService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private CorreoService correoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // GET – Listar todas
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER' , 'ROLE_ADMIN_EMPRESA')")
    @GetMapping("")
    public List<EmpresaDto> listAll() {
        return EmpresaDto.from(empresaService.findAll());
    }

    // GET – Buscar por ID
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER' , 'ROLE_ADMIN_EMPRESA')")
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

            // Asegurar que la lista de usuarios no sea null
            empresa.setUsuarios(new ArrayList<>());
            empresa.getUsuarios().add(usuarioActual); // ya lo asociamos antes de guardar

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

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> delete(@PathVariable long id) {
        try {
            empresaService.eliminarEmpresaYDependencias(id);
            return ResponseEntity.ok(Map.of("msg", "Empresa eliminada correctamente."));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no eliminada"));
        }
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

    @PostMapping("/empresa/enviar-correo")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> enviarCorreoEmpleados(@RequestBody Map<String, String> cuerpo) {
        String asunto = cuerpo.get("asunto");
        String mensaje = cuerpo.get("mensaje");

        // Obtener el usuario autenticado desde el contexto
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User admin = usuarioRepository.findByUsername(username);

        if (admin == null || admin.getEmpresa() == null) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", "No perteneces a ninguna empresa."));
        }

        Empresa empresa = admin.getEmpresa();
        List<User> empleados = userService.findByEmpresa(empresa);
        int enviados = 0;

        for (User e : empleados) {
            if (e.getEmail() != null && !e.getEmail().isBlank()) {
                correoService.enviarCorreo(e.getEmail(), asunto, mensaje);
                enviados++;
            }
        }

        return ResponseEntity.ok(Map.of("mensaje", "Correo enviado a " + enviados + " usuarios."));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    @PutMapping("/{id}/cambiar-clave")
    public ResponseEntity<?> cambiarClaveEmpresa(
            @PathVariable Long id,
            @RequestBody CambiarClaveEmpresaDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("==== [CAMBIO CLAVE EMPRESA] ====");
        System.out.println("Solicitado por: " + userDetails.getUsername());
        System.out.println("Empresa ID en ruta: " + id);
        System.out.println("DTO recibido: " + dto);

        try {
            Empresa empresa = empresaService.findById(id);
            if (empresa == null) {
                System.out.println("❌ Empresa no encontrada con ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empresa no encontrada");
            }

            User usuario = usuarioRepository.findByUsername(userDetails.getUsername());

            if (usuario == null) {
                System.out.println("❌ Usuario no encontrado por email: " + userDetails.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuario no autenticado");
            }

            if (usuario.getEmpresa() == null) {
                System.out.println("⚠️ Usuario no tiene empresa asociada.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes empresa asociada.");
            }

            Long empresaDelUsuarioId = usuario.getEmpresa().getId();
            System.out.println("Empresa del usuario: " + empresaDelUsuarioId);

            if (!empresaDelUsuarioId.equals(id)) {
                System.out.println("❌ Usuario no pertenece a esta empresa. Intento de cambiar ID: " + id);
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("No tienes permiso para modificar esta empresa.");
            }

            // Cambiar la clave
            String claveCodificada = passwordEncoder.encode(dto.getNuevaClave());
            empresa.setClaveAcceso(claveCodificada);
            empresaService.save(empresa);
            System.out.println("✅ Clave actualizada correctamente para la empresa: " + empresa.getNombre());

            // Enviar correo
            correoService.enviarCorreo(
                    usuario.getEmail(),
                    "🔐 Clave de acceso actualizada",
                    "Hola " + usuario.getUsername() + ",\n\nHas actualizado correctamente la clave de acceso de tu empresa: " + empresa.getNombre() + ".\n\nSi no fuiste tú, contacta con soporte inmediatamente."
            );
            System.out.println("📩 Correo de confirmación enviado a: " + usuario.getEmail());

            return ResponseEntity.ok(Map.of("mensaje", "Clave correctamente actualizada y correo enviado"));
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar clave: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la clave: " + e.getMessage());
        }
    }



}
