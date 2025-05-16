package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Empresa;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaDto {
    private Long id;
    private String nombre;
    private String claveAcceso;
    private int cantidadUsuarios;

    public static EmpresaDto from(Empresa entity) {
        EmpresaDto dto = new EmpresaDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setClaveAcceso(entity.getClaveAcceso());
        dto.setCantidadUsuarios(entity.getUsuarios().size());
        return dto;
    }

    public Empresa to() {
        Empresa entity = new Empresa();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setClaveAcceso(this.getClaveAcceso());
        return entity;
    }

    public static List<EmpresaDto> from(List<Empresa> list) {
        List<EmpresaDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<EmpresaDto>();
            for (Empresa entity : list) {
                dtos.add(EmpresaDto.from(entity));
            }
        }
        return dtos;
    }
}
