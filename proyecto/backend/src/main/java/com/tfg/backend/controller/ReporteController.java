package com.tfg.backend.controller;

import com.tfg.backend.model.dto.EmpresaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ReporteDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Reporte;
import com.tfg.backend.service.ReporteService;
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
@RequestMapping("/reportes")
@Controller
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping({""})
    public List<ReporteDto> listAll() {
        return ReporteDto.from(reporteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable long id) {
        Reporte reporteBD = reporteService.findById(id);
        if (reporteBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ReporteDto.from(reporteBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Reporte no encontrado"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ReporteDto reporteDto) {
        try {
            Reporte reporteBD = reporteDto.to();
            reporteService.save(reporteBD);
            return ResponseEntity.status(HttpStatus.OK).body(ReporteDto.from(reporteBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Reporte no guardada"));
        }
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Reporte reporteBD = reporteService.findById(id);
        if (reporteBD != null) {
            try {
                reporteService.delete(reporteBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ReporteDto.from(reporteBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Reporte no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Reporte no encontrada"));
        }
    }
}
