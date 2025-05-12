package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Proveedor;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProveedorDto {

    private Long id;
    private String nombre;
    private String telefono;
    private String email;

    public static ProveedorDto from(Proveedor entity) {
        ProveedorDto dto = new ProveedorDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setTelefono(entity.getTelefono());
        dto.setEmail(entity.getEmail());
        return dto;
    }

    public Proveedor to() {
        Proveedor entity = new Proveedor();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setTelefono(this.getTelefono());
        entity.setEmail(this.getEmail());
        return entity;
    }

    public static List<ProveedorDto> from(List<Proveedor> list) {
        List<ProveedorDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Proveedor entity : list) {
                dtos.add(ProveedorDto.from(entity));
            }
        }
        return dtos;
    }
}
