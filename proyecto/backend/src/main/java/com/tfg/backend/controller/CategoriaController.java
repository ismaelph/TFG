package com.tfg.backend.controller;

import com.tfg.backend.model.dto.CategoriaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.service.CategoriaService;
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
    private CategoriaService categoriaService;

    @GetMapping({""})
    public List<CategoriaDto> listAll() {
        return CategoriaDto.from(categoriaService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byNombre(@PathVariable String nombre) {
        Categoria categoriaBD = categoriaService.findByNombre(nombre);
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
            categoriaService.save(categoriaBd);
            return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBd));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no guardada"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Categoria categoriaBD = categoriaService.findById(id);
        if (categoriaBD != null) {
            try {
                categoriaService.delete(categoriaBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Categoria no encontrada"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody CategoriaDto categoriaDto) {
        Categoria categoriaBd = categoriaService.findById(id);
        if (categoriaBd != null) {
            try {
                categoriaBd = categoriaService.update(categoriaDto.to(), categoriaBd.getId());
                return ResponseEntity.status(HttpStatus.OK).body(CategoriaDto.from(categoriaBd));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Categoria no modificada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Categoria no encontrada"));
        }
    }
}
