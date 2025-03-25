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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    // Crear usuario con credenciales encriptadas
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null || strRoles.isEmpty()) {
      Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role.toLowerCase()) {
          case "superadmin":
            roles.add(roleRepository.findByName(RoleEnum.ROLE_SUPER_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
            break;
          case "admin_empresa":
            roles.add(roleRepository.findByName(RoleEnum.ROLE_ADMIN_EMPRESA)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
            break;
          case "empleado":
            roles.add(roleRepository.findByName(RoleEnum.ROLE_EMPLEADO)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
            break;
          default:
            roles.add(roleRepository.findByName(RoleEnum.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado.")));
            break;
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("¡El usuario ha sido registrado!"));
  }

}
