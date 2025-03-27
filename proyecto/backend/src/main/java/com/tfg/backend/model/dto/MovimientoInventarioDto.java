package com.tfg.backend.model.dto;

import com.tfg.backend.Enum.TipoMovimiento;
import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.model.entity.MovimientoInventario;
import com.tfg.backend.model.entity.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovimientoInventarioDto {
    private Long id;
    private ProductoDto producto;
    private UserDto usuario;
    private TipoMovimiento tipoMovimiento;
    private int cantidad;
    private String descripcion;

    public static MovimientoInventarioDto from(MovimientoInventario entity) {
        MovimientoInventarioDto dto = null;
        if (entity != null) {
            dto = new MovimientoInventarioDto();
            dto.setId(entity.getId());
            dto.setProducto(ProductoDto.from(entity.getProducto()));
            dto.setUsuario(UserDto.from(entity.getUsuario()));
            dto.setTipoMovimiento(entity.getTipoMovimiento());
            dto.setCantidad(entity.getCantidad());
            dto.setDescripcion(entity.getDescripcion());
        }
        return dto;
    }

    public static List<MovimientoInventarioDto> from(List<MovimientoInventario> list) {
        List<MovimientoInventarioDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (MovimientoInventario entity : list) {
                dtos.add(MovimientoInventarioDto.from(entity));
            }
        }
        return dtos;
    }

    public MovimientoInventario to() {
        MovimientoInventario entity = new MovimientoInventario();
        entity.setId(id);
        entity.setProducto(producto.to());
        entity.setUsuario(usuario.to());
        entity.setTipoMovimiento(tipoMovimiento);
        entity.setCantidad(cantidad);
        entity.setDescripcion(descripcion);
        return entity;
    }

}
