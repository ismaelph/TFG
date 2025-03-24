package com.tfg.backend.auth.controllers;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.auth.services.UserService;
import com.tfg.backend.model.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({""})
    public List<UserDto> listAll() {
        return UserDto.from(userService.findAll());
    }

    @GetMapping("/{nif}")
    public ResponseEntity<?> byId(@PathVariable String nif) {
        User cursoBd = userService.findByNif(nif);
        if (cursoBd != null) {
            return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(cursoBd));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no encontrado"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        try {
            User userBD = userDto.to();
            userService.save(userBD);
            return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(userBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no guardado"));
        }
    }

    @DeleteMapping("/{nif}")
    public ResponseEntity<?> delete(@PathVariable String nif) {
        User userBD = userService.findByNif(nif);
        if (userBD != null) {
            try {
                userService.delete(userBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(userBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no borrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Usuario no encontrado"));
        }
    }

    @PutMapping("/{nif}")
    public ResponseEntity<?> update(@PathVariable String nif, @RequestBody UserDto userdto) {
        User cursoBd = userService.findByNif(nif);
        if (cursoBd != null) {
            try {
                cursoBd = userService.update(userdto.to(), cursoBd.getId());
                return ResponseEntity.status(HttpStatus.OK).body(UserDto.from(cursoBd));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Usuario no modificado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Usuario no encontrado"));
        }
    }
}
