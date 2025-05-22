package com.tfg.backend.controller;

import com.tfg.backend.model.dto.AlmacenDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Almacen;
import com.tfg.backend.service.AlmacenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/almacenes")
@Slf4j
public class AlmacenController {

    @Autowired
    private AlmacenService almacenService;

    @GetMapping("")
    public List<AlmacenDto> listAll() {
        return AlmacenDto.from(almacenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Almacen almacen = almacenService.findById(id);
        if (almacen != null) {
            return ResponseEntity.ok(AlmacenDto.from(almacen));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Almacén no encontrado"));
    }

    @GetMapping("/empresa/{empresaId}")
    public List<AlmacenDto> findByEmpresaId(@PathVariable long empresaId) {
        return AlmacenDto.from(almacenService.findByEmpresaId(empresaId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody AlmacenDto dto) {
        try {
            Almacen almacen = dto.to();
            almacenService.save(almacen);
            return ResponseEntity.status(HttpStatus.CREATED).body(AlmacenDto.from(almacen));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Almacén no guardado"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody AlmacenDto dto) {
        Almacen actualizado = almacenService.update(dto.to(), id);
        if (actualizado != null) {
            return ResponseEntity.ok(AlmacenDto.from(actualizado));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Almacén no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Almacen almacen = almacenService.findById(id);
        if (almacen != null) {
            almacenService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Almacén no encontrado"));
    }
}
