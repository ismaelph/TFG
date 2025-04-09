package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.UserDto;
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
    private Double precio;
    private Integer cantidad;
    private Boolean usoInterno;
    private CategoriaDto categoria;
    private EmpresaDto empresa;
    private ProveedorDto proveedor;
    private UserDto creadoPor;

    public static ProductoDto from(Producto entity) {
        ProductoDto dto = null;
        if (entity != null) {
            dto = new ProductoDto();
            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setDescripcion(entity.getDescripcion());
            dto.setPrecio(entity.getPrecio());
            dto.setCantidad(entity.getCantidad());
            dto.setUsoInterno(entity.getUsoInterno());
            dto.setCategoria(CategoriaDto.from(entity.getCategoria()));
            dto.setEmpresa(EmpresaDto.from(entity.getEmpresa()));
            dto.setProveedor(ProveedorDto.from(entity.getProveedor()));
            dto.setCreadoPor(UserDto.from(entity.getCreadoPor()));
        }
        return dto;
    }

    public static List<ProductoDto> from(List<Producto> list) {
        List<ProductoDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<ProductoDto>();
            for (Producto entity : list) {
                dtos.add(ProductoDto.from(entity));
            }
        }
        return dtos;
    }

    public Producto to(){
        Producto entity = new Producto();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setDescripcion(this.getDescripcion());
        entity.setPrecio(this.getPrecio());
        entity.setCantidad(this.getCantidad());
        entity.setUsoInterno(this.getUsoInterno());
        entity.setCategoria(this.getCategoria().to());
        entity.setEmpresa(this.getEmpresa().to());
        entity.setProveedor(this.getProveedor().to());
        entity.setCreadoPor(this.getCreadoPor().to());
        return entity;
    }
}
