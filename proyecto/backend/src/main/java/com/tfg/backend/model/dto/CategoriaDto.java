package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Categoria;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaDto {

    private Long id;
    private String nombre;

    public static CategoriaDto from(Categoria entity) {
        CategoriaDto dto = new CategoriaDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        return dto;
    }

    public Categoria to() {
        Categoria entity = new Categoria();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        return entity;
    }

    public static List<CategoriaDto> from(List<Categoria> list) {
        List<CategoriaDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Categoria entity : list) {
                dtos.add(CategoriaDto.from(entity));
            }
        }
        return dtos;
    }
}
