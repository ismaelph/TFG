package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Categoria;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriaDto {
    private Long id;
    private String nombre;
    private String descripcion;

    public static CategoriaDto from(Categoria entity) {
        CategoriaDto dto = null;
        if (entity != null) {
            dto = new CategoriaDto();
            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setDescripcion(entity.getDescripcion());
        }
        return dto;
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

    public Categoria to() {
        Categoria entity = new Categoria();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setDescripcion(descripcion);
        return entity;
    }
}
