package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Producto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDto {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
    private boolean usoInterno;
    private LocalDateTime fechaIngreso;

    private Long empresaId;
    private Long categoriaId;
    private Long proveedorId;
    private Long usuarioId;

    public static ProductoDto from(Producto entity) {
        ProductoDto dto = new ProductoDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setCantidad(entity.getCantidad());
        dto.setUsoInterno(entity.isUsoInterno());
        dto.setFechaIngreso(entity.getFechaIngreso());

        if (entity.getEmpresa() != null) dto.setEmpresaId(entity.getEmpresa().getId());
        if (entity.getCategoria() != null) dto.setCategoriaId(entity.getCategoria().getId());
        if (entity.getProveedor() != null) dto.setProveedorId(entity.getProveedor().getId());
        if (entity.getUsuario() != null) dto.setUsuarioId(entity.getUsuario().getId());

        return dto;
    }

    public Producto to() {
        Producto producto = new Producto();
        producto.setId(this.getId());
        producto.setNombre(this.getNombre());
        producto.setPrecio(this.getPrecio());
        producto.setCantidad(this.getCantidad());
        producto.setUsoInterno(this.isUsoInterno());
        // fechaIngreso no se establece manualmente, se autogenera
        return producto;
    }

    public static List<ProductoDto> from(List<Producto> list) {
        List<ProductoDto> dtos = new ArrayList<>();
        if (list != null) {
            for (Producto entity : list) {
                dtos.add(ProductoDto.from(entity));
            }
        }
        return dtos;
    }


}
