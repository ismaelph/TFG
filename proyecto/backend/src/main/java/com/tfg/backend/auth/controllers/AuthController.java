package com.tfg.backend.auth.controllers;

import com.tfg.backend.auth.jwt.JwtUtils;
import com.tfg.backend.auth.models.Role;
import com.tfg.backend.auth.models.RoleEnum;
import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.payload.request.LoginRequest;
import com.tfg.backend.auth.payload.request.SignupRequest;
import com.tfg.backend.auth.payload.response.JwtResponse;
import com.tfg.backend.auth.payload.response.MessageResponse;
import com.tfg.backend.auth.repository.RoleRepository;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.service.CorreoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
///api/auth
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  private CorreoService correoService;


  /*
  {"username": "admin", "password":"castelar"}
   */
  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);
    
    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
                         userDetails.getId(), 
                         userDetails.getUsername(), 
                         userDetails.getEmail(), 
                         roles));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: ¡El nombre de usuario ya está en uso!"));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse("Error: ¡El email de usuario ya está en uso!"));
    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
               signUpRequest.getEmail(),
               encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
        case "admin":
          Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
          roles.add(adminRole);

          break;
        case "admin_empresa":
          Role modRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN_EMPRESA)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
          roles.add(modRole);
          break;
        case "empleado":
            Role empleadoRole = roleRepository.findByName(RoleEnum.ROLE_EMPLEADO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
            roles.add(empleadoRole);
          break;
        default:
          Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
          roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("¡El usuario ha sido registrado!"));
  }

  @PostMapping("/forgot-password")
  public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
    String email = body.get("email");
    System.out.println("📩 Solicitud de recuperación para: " + email);

    User user = userRepository.findByEmail(email).orElse(null);
    if (user == null) {
      System.out.println("❌ No existe usuario con ese email.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(new MessageResponse("No existe un usuario con ese email."));
    }

    String token = UUID.randomUUID().toString();
    user.setResetToken(token);
    user.setResetTokenExpiration(Instant.now().plus(Duration.ofHours(1)));
    userRepository.save(user);

    String enlace = "http://localhost:4200/auth/reset-password?token=" + token;
    System.out.println("✅ Token generado: " + token);
    System.out.println("🔗 Enlace de recuperación: " + enlace);

    correoService.enviarCorreo(
            user.getEmail(),
            "Recuperación de contraseña",
            "Haz clic en el siguiente enlace para restablecer tu contraseña: " + enlace
    );
    System.out.println("📬 Correo enviado a " + user.getEmail());

    return ResponseEntity.ok(new MessageResponse("Correo enviado con instrucciones para restablecer tu contraseña."));
  }


  @PostMapping("/reset-password")
  public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
    String token = body.get("token");
    String nuevaPassword = body.get("nuevaPassword");

    System.out.println("🔐 Intentando restablecer contraseña con token: " + token);

    User user = userRepository.findByResetToken(token).orElse(null);

    if (user == null) {
      System.out.println("❌ Token no encontrado.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new MessageResponse("Token inválido o expirado."));
    }

    if (user.getResetTokenExpiration() == null || user.getResetTokenExpiration().isBefore(Instant.now())) {
      System.out.println("❌ Token expirado.");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(new MessageResponse("Token inválido o expirado."));
    }

    user.setPassword(encoder.encode(nuevaPassword));
    user.setResetToken(null);
    user.setResetTokenExpiration(null);
    userRepository.save(user);

    System.out.println("✅ Contraseña restablecida correctamente para: " + user.getEmail());

    return ResponseEntity.ok(new MessageResponse("Contraseña restablecida correctamente."));
  }



}
