package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.enums.TipoMovimiento;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoProductoDto {

    private Long id;
    private Long productoId;
    private String productoNombre;
    private Long empresaId;
    private Long usuarioId;
    private String usuarioNombre;

    private Integer cantidad;
    private TipoMovimiento tipo;
    private String observaciones;
    private LocalDateTime fecha;

    public static MovimientoProductoDto from(MovimientoProducto entity) {
        MovimientoProductoDto dto = new MovimientoProductoDto();
        dto.setId(entity.getId());
        dto.setCantidad(entity.getCantidad());
        dto.setTipo(entity.getTipo());
        dto.setObservaciones(entity.getObservaciones());
        dto.setFecha(entity.getFecha());

        if (entity.getProducto() != null) {
            dto.setProductoId(entity.getProducto().getId());
            dto.setProductoNombre(entity.getProducto().getNombre());
        }

        if (entity.getEmpresa() != null) {
            dto.setEmpresaId(entity.getEmpresa().getId());
        }

        if (entity.getUsuario() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
            dto.setUsuarioNombre(entity.getUsuario().getUsername());
        }

        return dto;
    }

    public MovimientoProducto to() {
        MovimientoProducto mov = new MovimientoProducto();
        mov.setId(this.getId());
        mov.setCantidad(this.getCantidad());
        mov.setTipo(this.getTipo());
        mov.setObservaciones(this.getObservaciones());
        // producto, empresa y usuario se inyectan desde el servicio/controlador
        return mov;
    }

    public static List<MovimientoProductoDto> from(List<MovimientoProducto> list) {
        List<MovimientoProductoDto> dtos = new ArrayList<>();
        if (list != null) {
            for (MovimientoProducto entity : list) {
                dtos.add(from(entity));
            }
        }
        return dtos;
    }
}

