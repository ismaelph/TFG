package com.tfg.backend.controller;

import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ProveedorDto;
import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.service.ProveedoresService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/proveedores")
@Controller
public class ProveedorController {
    @Autowired
    private ProveedoresService proveedoresService;

    @GetMapping({""})
    public List<ProveedorDto> listAll() {
        return ProveedorDto.from(proveedoresService.findAll());
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> byId(@PathVariable String email) {
        Proveedor proveedorBD = proveedoresService.findByEmail(email);
        if (proveedorBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no encontrado/a"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProveedorDto proveedorDto) {
        try {
            Proveedor proveedorBD = proveedorDto.to();
            proveedoresService.save(proveedorBD);
            return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no guardado/a"));
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable String email) {
        Proveedor proveedorBD = proveedoresService.findByEmail(email);
        if (proveedorBD != null) {
            try {
                proveedoresService.delete(proveedorBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no borrado/a"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Proveedor no encontrado/a"));
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> update(@PathVariable String nombre, @RequestBody ProveedorDto proveedorDto) {
        Proveedor proveedorBD = proveedoresService.findByNombre(nombre);
        if (proveedorBD != null) {
            try {
                proveedorBD = proveedoresService.update(proveedorDto.to(), proveedorBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no modificado/a"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Proveedor no encontrado/a"));
        }
    }
}
