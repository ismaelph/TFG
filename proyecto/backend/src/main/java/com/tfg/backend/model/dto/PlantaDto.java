package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Almacen;
import com.tfg.backend.model.entity.Planta;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlantaDto {

    private Long id;
    private String nombre;
    private Integer numero;
    private Long almacenId;

    public static PlantaDto from(Planta entity) {
        PlantaDto dto = new PlantaDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setNumero(entity.getNumero());
        dto.setAlmacenId(entity.getAlmacen() != null ? entity.getAlmacen().getId() : null);
        return dto;
    }

    public Planta to() {
        Planta entity = new Planta();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setNumero(this.getNumero());

        if (this.getAlmacenId() != null) {
            Almacen almacen = new Almacen();
            almacen.setId(this.getAlmacenId());
            entity.setAlmacen(almacen);
        }

        return entity;
    }


    public static List<PlantaDto> from(List<Planta> list) {
        List<PlantaDto> dtos = new ArrayList<>();
        for (Planta entity : list) {
            dtos.add(from(entity));
        }
        return dtos;
    }
}
