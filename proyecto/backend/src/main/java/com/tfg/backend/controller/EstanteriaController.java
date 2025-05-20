package com.tfg.backend.controller;

import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.EstanteriaDto;
import com.tfg.backend.model.entity.Estanteria;
import com.tfg.backend.service.EstanteriaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/estanterias")
@Slf4j
public class EstanteriaController {

    @Autowired
    private EstanteriaService estanteriaService;

    @GetMapping("")
    public List<EstanteriaDto> listAll() {
        return EstanteriaDto.from(estanteriaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable long id) {
        Estanteria estanteria = estanteriaService.findById(id);
        if (estanteria != null) {
            return ResponseEntity.ok(EstanteriaDto.from(estanteria));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Estantería no encontrada"));
    }

    @GetMapping("/planta/{plantaId}")
    public List<EstanteriaDto> findByPlantaId(@PathVariable long plantaId) {
        return EstanteriaDto.from(estanteriaService.findByPlantaId(plantaId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody EstanteriaDto dto) {
        try {
            Estanteria estanteria = dto.to();
            estanteriaService.save(estanteria);
            return ResponseEntity.status(HttpStatus.CREATED).body(EstanteriaDto.from(estanteria));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Estantería no guardada"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody EstanteriaDto dto) {
        Estanteria actualizada = estanteriaService.update(dto.to(), id);
        if (actualizada != null) {
            return ResponseEntity.ok(EstanteriaDto.from(actualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Estantería no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Estanteria estanteria = estanteriaService.findById(id);
        if (estanteria != null) {
            estanteriaService.delete(id);
            return ResponseEntity.ok("Estantería eliminada correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Estantería no encontrada"));
    }
}
