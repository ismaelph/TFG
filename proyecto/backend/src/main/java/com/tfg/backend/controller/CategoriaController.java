package com.tfg.backend.controller;

import com.tfg.backend.model.dto.CategoriaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.service.CategoriasService;
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
@RequestMapping("/categorias")
@Controller
public class CategoriaController {
    @Autowired
    private CategoriasService categoriasService;

    @GetMapping({""})
    public List<CategoriaDto> listAll() {
        return CategoriaDto.from(categoriasService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byId(@PathVariable String nombre) {
        Categoria categoriaBD = categoriasService.findByNombre(nombre);
        if (categoriaBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no encontrada"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody CategoriaDto categoriaDto) {
        try {
            Categoria categoriaBd = categoriaDto.to();
            categoriasService.save(categoriaBd);
            return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no guardada"));
        }
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        Categoria categoriaBD = categoriasService.findByNombre(nombre);
        if (categoriaBD != null) {
            try {
                categoriasService.delete(categoriaBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Categoria no encontrada"));
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> update(@PathVariable String nombre, @RequestBody CategoriaDto cursoDto) {
        Categoria cursoBd = categoriasService.findByNombre(nombre);
        if (cursoBd != null) {
            try {
                cursoBd = categoriasService.update(cursoDto.to(), cursoBd.getId());
                return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(cursoBd));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no modificada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Categoria no encontrada"));
        }
    }
}
