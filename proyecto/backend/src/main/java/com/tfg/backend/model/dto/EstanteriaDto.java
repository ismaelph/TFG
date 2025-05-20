package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Estanteria;
import com.tfg.backend.model.entity.Planta;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstanteriaDto {

    private Long id;
    private String codigo;
    private Long plantaId;

    public static EstanteriaDto from(Estanteria entity) {
        EstanteriaDto dto = new EstanteriaDto();
        dto.setId(entity.getId());
        dto.setCodigo(entity.getCodigo());
        dto.setPlantaId(entity.getPlanta() != null ? entity.getPlanta().getId() : null);
        return dto;
    }

    public Estanteria to() {
        Estanteria entity = new Estanteria();
        entity.setId(this.getId());
        entity.setCodigo(this.getCodigo());

        if (this.getPlantaId() != null) {
            Planta planta = new Planta();
            planta.setId(this.getPlantaId());
            entity.setPlanta(planta);
        }

        return entity;
    }


    public static List<EstanteriaDto> from(List<Estanteria> list) {
        List<EstanteriaDto> dtos = new ArrayList<>();
        for (Estanteria entity : list) {
            dtos.add(from(entity));
        }
        return dtos;
    }
}
