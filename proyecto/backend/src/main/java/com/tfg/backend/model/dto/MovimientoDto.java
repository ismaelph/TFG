package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.UserDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Movimiento;
import com.tfg.backend.model.entity.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MovimientoDto {
    private Long id;
    private ProductoDto producto;
    private UserDto usuario;
    private TipoMovimiento tipoMovimiento;
    private Integer cantidad;

    public static MovimientoDto from(Movimiento entity) {
        MovimientoDto dto = null;
        if (entity != null) {
            dto = new MovimientoDto();
            dto.setId(entity.getId());
            dto.setProducto(ProductoDto.from(entity.getProducto()));
            dto.setUsuario(UserDto.from(entity.getUsuario()));
            dto.setTipoMovimiento(entity.getTipo());
            dto.setCantidad(entity.getCantidad());
        }
        return dto;
    }

    public static List<MovimientoDto> from(List<Movimiento> list) {
        List<MovimientoDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<MovimientoDto>();
            for (Movimiento entity : list) {
                dtos.add(MovimientoDto.from(entity));
            }
        }
        return dtos;
    }

    public Movimiento to(){
        Movimiento entity = new Movimiento();
        entity.setId(this.getId());
        entity.setProducto(this.getProducto().to());
        entity.setUsuario(this.getUsuario().to());
        entity.setTipo(this.getTipoMovimiento());
        entity.setCantidad(this.getCantidad());
        return entity;
    }
}
