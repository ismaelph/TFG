package com.tfg.backend.auth.controllers;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.models.UserDto;
import com.tfg.backend.auth.payload.response.MessageResponse;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.auth.services.UserService;
import com.tfg.backend.model.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<?> obtenerPerfil() {
        User usuarioActual = userService.obtenerUsuarioActual();
        Map<String, Object> perfil = new HashMap<>();
        perfil.put("username", usuarioActual.getUsername());
        perfil.put("email", usuarioActual.getEmail());
        perfil.put("empresa", usuarioActual.getEmpresa() != null ? usuarioActual.getEmpresa().getNombre() : "Sin empresa");
        perfil.put("rol", usuarioActual.getRoles().stream().findFirst().orElse(null).getName());
        perfil.put("fotoPerfil", usuarioActual.getFotoPerfil());
        return ResponseEntity.ok(perfil);
    }

    @PutMapping("/profile/photo")
    public ResponseEntity<?> actualizarFotoPerfil(@RequestBody Map<String, String> request) {
        String nuevaFoto = request.get("fotoPerfil");
        User usuarioActual = userService.obtenerUsuarioActual();
        usuarioActual.setFotoPerfil(nuevaFoto);
        userRepository.save(usuarioActual);

        return ResponseEntity.ok(new MessageResponse("Foto de perfil actualizada correctamente"));
    }

    @GetMapping({""})
  public List<UserDto> listAll() {
    return UserDto.from(userService.findAll());
  }
  @GetMapping("/{id}")
  public ResponseEntity<?> byId(@PathVariable long id) {
    User userBd = userService.findById(id);
    if (userBd != null) {
      return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(userBd));
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no encontrada"));
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable long id) {
    User userBd = userService.findById(id);
    if (userBd != null) {
      try {
        userService.delete(userBd.getId());
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(userBd));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no borrada"));
      }
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Usuario no encontrada"));
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserDto userDto) {
    User userBd = userService.findById(id);
    if (userBd != null) {
      try {
        userBd = userService.update(userDto.to(), userBd.getId());
        return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(userBd));
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no modificada"));
      }
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Usuario no encontrada"));
    }
  }
}
