package com.tfg.backend.controller;

import com.tfg.backend.Enum.TipoMovimiento;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.MovimientoInventarioDto;
import com.tfg.backend.model.entity.MovimientoInventario;
import com.tfg.backend.service.MovimientosInventarioService;
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
@RequestMapping("/movimientos")
@Controller
public class MovimientoInventarioController {
    @Autowired
    private MovimientosInventarioService movimientosInventarioService;

    @GetMapping({""})
    public List<MovimientoInventarioDto> listAll() {
        return MovimientoInventarioDto.from(movimientosInventarioService.findAll());
    }

    @GetMapping("/{tipoMovimiento}")
    public ResponseEntity<?> byId(@PathVariable TipoMovimiento tipoMovimiento) {
        List<MovimientoInventario> movimientos = movimientosInventarioService.findByTipoMovimiento(String.valueOf(tipoMovimiento));
        if (movimientos != null) {
            return ResponseEntity.ok(MovimientoInventarioDto.from(movimientos));
        } else {
            return ResponseEntity.badRequest().body(ErrorDto.from("Movimiento no encontrado"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody MovimientoInventarioDto movimientoInventarioDto) {
        try {
            MovimientoInventario movimientoBD = movimientoInventarioDto.to();
            movimientosInventarioService.save(movimientoBD);
            return ResponseEntity.status(HttpStatus.OK).body(MovimientoInventarioDto.from(movimientoBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Movimiento no guardado"));
        }
    }

    @DeleteMapping("/{tipoMovimiento}")
    public ResponseEntity<?> delete(@PathVariable TipoMovimiento tipoMovimiento) {
        List<MovimientoInventario> movimientos = movimientosInventarioService.findByTipoMovimiento(String.valueOf(tipoMovimiento));

        if (movimientos != null && !movimientos.isEmpty()) {
            try {
                for (MovimientoInventario movimiento : movimientos) {
                    movimientosInventarioService.delete(movimiento.getId());
                }
                return ResponseEntity.status(HttpStatus.OK).body("Movimientos eliminados correctamente");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Error al eliminar los movimientos"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Movimiento no encontrado"));
        }
    }
}
