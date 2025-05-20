package com.tfg.backend.controller;

import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.PlantaDto;
import com.tfg.backend.model.entity.Planta;
import com.tfg.backend.service.PlantaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/plantas")
@Slf4j
public class PlantaController {

    @Autowired
    private PlantaService plantaService;

    @GetMapping("")
    public List<PlantaDto> listAll() {
        return PlantaDto.from(plantaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Planta planta = plantaService.findById(id);
        if (planta != null) {
            return ResponseEntity.ok(PlantaDto.from(planta));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Planta no encontrada"));
    }

    @GetMapping("/almacen/{almacenId}")
    public List<PlantaDto> findByAlmacenId(@PathVariable long almacenId) {
        return PlantaDto.from(plantaService.findByAlmacenId(almacenId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody PlantaDto dto) {
        try {
            Planta planta = dto.to();
            plantaService.save(planta);
            return ResponseEntity.status(HttpStatus.CREATED).body(PlantaDto.from(planta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Planta no guardada"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody PlantaDto dto) {
        Planta actualizada = plantaService.update(dto.to(), id);
        if (actualizada != null) {
            return ResponseEntity.ok(PlantaDto.from(actualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Planta no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Planta planta = plantaService.findById(id);
        if (planta != null) {
            plantaService.delete(id);
            return ResponseEntity.ok("Planta eliminada correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Planta no encontrada"));
    }
}
