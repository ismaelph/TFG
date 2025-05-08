package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Empresa;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmpresaDto {
    private Long id;
    private String nombre;
    private String claveAcceso;

    public static EmpresaDto from(Empresa empresa) {
        if (empresa == null) return null;
        return EmpresaDto.builder()
                .id(empresa.getId())
                .nombre(empresa.getNombre())
                .claveAcceso(empresa.getClaveAcceso())
                .build();
    }

    public Empresa to() {
        return Empresa.builder()
                .id(this.id)
                .nombre(this.nombre)
                .claveAcceso(this.claveAcceso)
                .build();
    }
}
