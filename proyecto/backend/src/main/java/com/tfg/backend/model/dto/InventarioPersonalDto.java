package com.tfg.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventarioPersonalDto {
    private Long productoId;
    private String nombre;
    private Integer cantidad;
    private String categoriaNombre;
    private String proveedorNombre;
}
