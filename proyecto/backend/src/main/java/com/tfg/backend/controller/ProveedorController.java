package com.tfg.backend.controller;

import com.tfg.backend.model.dto.CategoriaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ProveedorDto;
import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.service.ProveedorService;
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
    private ProveedorService proveedorService;

    @GetMapping({""})
    public List<ProveedorDto> listAll() {
        return ProveedorDto.from(proveedorService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byNombre(@PathVariable String nombre) {
        Proveedor proveedorBD = proveedorService.findByNombre(nombre);
        if (proveedorBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no encontrado"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProveedorDto proveedorDto) {
        try {
            Proveedor proveedorBD = proveedorDto.to();
            proveedorService.save(proveedorBD);
            return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no guardado"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Proveedor proveedorBD = proveedorService.findById(id);
        if (proveedorBD != null) {
            try {
                proveedorService.delete(proveedorBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no borrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Proveedor no encontrado"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody ProveedorDto proveedorDto) {
        Proveedor proveedorBD = proveedorService.findById(id);
        if (proveedorBD != null) {
            try {
                proveedorBD = proveedorService.update(proveedorDto.to(), proveedorBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProveedorDto.from(proveedorBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Proveedor no modificado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Proveedor no encontrado"));
        }
    }
}
