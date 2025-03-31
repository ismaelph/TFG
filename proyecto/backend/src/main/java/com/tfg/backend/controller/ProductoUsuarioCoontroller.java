package com.tfg.backend.controller;

import com.tfg.backend.Enum.TipoMovimiento;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.ProductoUsuarioDto;
import com.tfg.backend.model.entity.ProductoUsuario;
import com.tfg.backend.service.ProductosUsuarioService;
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
@RequestMapping("/productos_del_usuario")
@Controller
public class ProductoUsuarioCoontroller {
    @Autowired
    private ProductosUsuarioService productosUsuarioService;

    @GetMapping({""})
    public List<ProductoUsuarioDto> listAll() {
        return ProductoUsuarioDto.from(productosUsuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable long id) {
        ProductoUsuario movimientos = productosUsuarioService.findById(id);
        if (movimientos != null) {
            return ResponseEntity.ok(ProductoUsuarioDto.from(movimientos));
        } else {
            return ResponseEntity.badRequest().body(ErrorDto.from("Producto del usuario no encontrado"));
        }
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        ProductoUsuario empresaBD = productosUsuarioService.findById(id);
        if (empresaBD != null) {
            try {
                productosUsuarioService.delete(empresaBD.getId());
                return ResponseEntity.status(HttpStatus.OK).body(ProductoUsuarioDto.from(empresaBD));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Producto del usuario no borrada"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Producto del usuario no encontrada"));
        }
    }
}
