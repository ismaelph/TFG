package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Producto;
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
        System.out.println("➡ CONVERT -> SolicitudMovimiento ID: " + entity.getId());
        System.out.println("   - Producto ID: " + (entity.getProducto() != null ? entity.getProducto().getId() : "null"));
        System.out.println("   - Usuario ID: " + (entity.getUsuario() != null ? entity.getUsuario().getId() : "null"));

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
        System.out.println("🟡 DTO.to(): productoId = " + this.productoId + ", usuarioId = " + this.usuarioId);

        SolicitudMovimiento solicitud = new SolicitudMovimiento();
        solicitud.setId(this.getId());
        solicitud.setCantidadSolicitada(this.getCantidadSolicitada());
        System.out.println(solicitud.getCantidadSolicitada());
        solicitud.setMotivo(this.getMotivo());
        solicitud.setEstado(this.getEstado());
        solicitud.setFechaSolicitud(this.getFechaSolicitud());
        solicitud.setFechaResolucion(this.getFechaResolucion());
        solicitud.setRespuestaAdmin(this.getRespuestaAdmin());

        if (this.productoId != null) {
            Producto producto = new Producto();
            producto.setId(this.productoId);
            solicitud.setProducto(producto);
        }

        if (this.usuarioId != null) {
            User user = new User();
            user.setId(this.usuarioId);
            solicitud.setUsuario(user);
        }

        return solicitud;
    }


    public static List<SolicitudMovimientoDto> from(List<SolicitudMovimiento> list) {
        List<SolicitudMovimientoDto> dtos = new ArrayList<>();
        for (SolicitudMovimiento entity : list) {
            dtos.add(from(entity));
        }
        return dtos;
    }
}
