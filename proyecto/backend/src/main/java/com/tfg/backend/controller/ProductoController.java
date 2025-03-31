package com.tfg.backend.controller;

import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ProductoDto;
import com.tfg.backend.model.entity.Producto;
import com.tfg.backend.service.ProductosService;
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
@RequestMapping("/productos")
@Controller
public class ProductoController {
    @Autowired
    private ProductosService productoService;

    @GetMapping({""})
    public List<ProductoDto> listAll() {
        return ProductoDto.from(productoService.findAll());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<?> byId(@PathVariable String nombre) {
        Producto productoBD = productoService.findByNombre(nombre);
        if (productoBD != null) {
            return ResponseEntity.status(HttpStatus.OK).body(ProductoDto.from(productoBD));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Producto no encontrado"));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody ProductoDto productoDto) {
        try {
            Producto productoBD = productoDto.to();
            productoService.save(productoBD);
            return ResponseEntity.status(HttpStatus.OK).body(ProductoDto.from(productoBD));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Producto no guardado"));
        }
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> delete(@PathVariable String nombre) {
        Producto productoBD = productoService.findByNombre(nombre);
        if (productoBD != null) {
            try {
                productoService.delete(productoBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProductoDto.from(productoBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Producto no borrado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Producto no encontrado"));
        }
    }

    @PutMapping("/{nombre}")
    public ResponseEntity<?> update(@PathVariable String nombre, @RequestBody ProductoDto proveedorDto) {
        Producto proveedorBD = productoService.findByNombre(nombre);
        if (proveedorBD != null) {
            try {
                proveedorBD = productoService.update(proveedorDto.to(), proveedorBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProductoDto.from(proveedorBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Producto no modificado"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Producto no encontrado"));
        }
    }
}
