package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.model.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Double precio;
    private Integer cantidad;
    private Boolean usoInterno;
    private CategoriaDto categoria;
    private ProveedorDto proveedor;
    private UserDto administrador;

    public static ProductoDto from(Producto entity) {
        ProductoDto dto = null;
        if (entity != null) {
            dto = new ProductoDto();
            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setDescripcion(entity.getDescripcion());
            dto.setImagen(entity.getImagen());
            dto.setPrecio(entity.getPrecio());
            dto.setCantidad(entity.getCantidad());
            dto.setUsoInterno(entity.getUsoInterno());
        }
        return dto;
    }

    public static List<ProductoDto> from(List<Producto> list) {
        List<ProductoDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Producto entity : list) {
                dtos.add(ProductoDto.from(entity));
            }
        }
        return dtos;
    }

    public Producto to() {
        Producto entity = new Producto();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setDescripcion(descripcion);
        entity.setImagen(imagen);
        entity.setPrecio(precio);
        entity.setCantidad(cantidad);
        entity.setUsoInterno(usoInterno);
        entity.setCategoria(categoria.to());
        entity.setProveedor(proveedor.to());
        entity.setAdministrador(administrador.to());
        return entity;
    }
}
