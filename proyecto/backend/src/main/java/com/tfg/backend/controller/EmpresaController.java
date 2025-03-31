package com.tfg.backend.controller;

import com.tfg.backend.model.dto.EmpresaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.service.EmpresaService;
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
@RequestMapping("/empresas")
@Controller
public class EmpresaController {
    @Autowired
    private EmpresaService empresaService;

    @GetMapping({""})
    public List<EmpresaDto> listAll() {
        return EmpresaDto.from(empresaService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byId(@PathVariable String nombre) {
        Empresa empresaBD = empresaService.findByNombre(nombre);
        if (empresaBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no encontrada"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody EmpresaDto empresaDto) {
        try {
            Empresa empresaBD = empresaDto.to();
            empresaService.save(empresaBD);
            return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no guardada"));
        }
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        Empresa empresaBD = empresaService.findByNombre(nombre);
        if (empresaBD != null) {
            try {
                empresaService.delete(empresaBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> update(@PathVariable String nombre, @RequestBody EmpresaDto empresaDTO) {
        Empresa empresaBD = empresaService.findByNombre(nombre);
        if (empresaBD != null) {
            try {
                empresaBD = empresaService.update(empresaDTO.to(), empresaBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(EmpresaDto.from(empresaBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Empresa no modificada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Empresa no encontrada"));
        }
    }
}
