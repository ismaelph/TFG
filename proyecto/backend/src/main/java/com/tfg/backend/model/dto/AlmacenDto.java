package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Almacen;
import com.tfg.backend.model.entity.Empresa;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlmacenDto {

    private Long id;
    private String nombre;
    private String direccion;
    private Long empresaId;

    public static AlmacenDto from(Almacen entity) {
        AlmacenDto dto = new AlmacenDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDireccion(entity.getDireccion());
        dto.setEmpresaId(entity.getEmpresa() != null ? entity.getEmpresa().getId() : null);
        return dto;
    }

    public Almacen to() {
        Almacen entity = new Almacen();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setDireccion(this.getDireccion());

        if (this.getEmpresaId() != null) {
            Empresa empresa = new Empresa();
            empresa.setId(this.getEmpresaId());
            entity.setEmpresa(empresa);
        }

        return entity;
    }


    public static List<AlmacenDto> from(List<Almacen> list) {
        List<AlmacenDto> dtos = new ArrayList<>();
        for (Almacen entity : list) {
            dtos.add(from(entity));
        }
        return dtos;
    }
}
