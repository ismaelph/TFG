package com.tfg.backend.model.dto;

import com.tfg.backend.model.entity.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProveedorDto {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;

    public static ProveedorDto from(Proveedor entity) {
        ProveedorDto dto = null;
        if (entity != null) {
            dto = new ProveedorDto();
            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setEmail(entity.getEmail());
            dto.setTelefono(entity.getTelefono());
            dto.setDireccion(entity.getDireccion());
        }
        return dto;
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

    public Proveedor to() {
        Proveedor entity = new Proveedor();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setEmail(email);
        entity.setTelefono(telefono);
        entity.setDireccion(direccion);
        return entity;
    }
}
