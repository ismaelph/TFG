package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.model.entity.Proveedor;
import com.tfg.backend.model.entity.Reporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReporteDto {
    private Long id;
    private UserDto usuario;
    private String nombreReporte;
    private String archivoPdf;

    public static ReporteDto from(Reporte entity) {
        ReporteDto dto = null;
        if (entity != null) {
            dto = new ReporteDto();
            dto.setId(entity.getId());
            dto.setUsuario(UserDto.from(entity.getUsuario()));
            dto.setNombreReporte(entity.getNombreReporte());
            dto.setArchivoPdf(entity.getArchivoPdf());
        }
        return dto;
    }

    public static List<ReporteDto> from(List<Reporte> list) {
        List<ReporteDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Reporte entity : list) {
                dtos.add(ReporteDto.from(entity));
            }
        }
        return dtos;
    }

    public Reporte to() {
        Reporte entity = new Reporte();
        entity.setId(id);
        entity.setUsuario(usuario.to());
        entity.setNombreReporte(nombreReporte);
        entity.setArchivoPdf(archivoPdf);
        return entity;
    }
}
