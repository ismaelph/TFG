package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.enums.EstadoSolicitud;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudMovimientoDto {

    private Long id;
    private Long productoId;
    private Long usuarioId;
    private Integer cantidadSolicitada;
    private EstadoSolicitud estado;
    private String motivo;
    private Instant fechaSolicitud;
    private Instant fechaResolucion;
    private String respuestaAdmin;

    public static SolicitudMovimientoDto from(SolicitudMovimiento entity) {
        SolicitudMovimientoDto dto = new SolicitudMovimientoDto();
        dto.setId(entity.getId());
        dto.setProductoId(entity.getProducto() != null ? entity.getProducto().getId() : null);
        dto.setUsuarioId(entity.getUsuario() != null ? entity.getUsuario().getId() : null);
        dto.setCantidadSolicitada(entity.getCantidadSolicitada());
        dto.setEstado(entity.getEstado());
        dto.setMotivo(entity.getMotivo());
        dto.setFechaSolicitud(entity.getFechaSolicitud());
        dto.setFechaResolucion(entity.getFechaResolucion());
        dto.setRespuestaAdmin(entity.getRespuestaAdmin());
        return dto;
    }

    public SolicitudMovimiento to() {
        SolicitudMovimiento entity = new SolicitudMovimiento();
        entity.setId(this.getId());
        entity.setCantidadSolicitada(this.getCantidadSolicitada());
        entity.setEstado(this.getEstado());
        entity.setMotivo(this.getMotivo());
        entity.setFechaSolicitud(this.getFechaSolicitud());
        entity.setFechaResolucion(this.getFechaResolucion());
        entity.setRespuestaAdmin(this.getRespuestaAdmin());
        return entity;
    }

    public static List<SolicitudMovimientoDto> from(List<SolicitudMovimiento> list) {
        List<SolicitudMovimientoDto> dtos = new ArrayList<>();
        for (SolicitudMovimiento entity : list) {
            dtos.add(from(entity));
        }
        return dtos;
    }
}
