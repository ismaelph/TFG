package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.model.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaDto {
    private Long id;
    private String nombre;
    private String cif;
    private String direccion;
    private String ciudad;
    private String pais;
    private String telefono;
    private String email;
    private String web;

    public static EmpresaDto from(Empresa entity) {
        EmpresaDto dto = null;
        if (entity != null) {
            dto = new EmpresaDto();
            dto.setId(entity.getId());
            dto.setNombre(entity.getNombre());
            dto.setCif(entity.getCif());
            dto.setDireccion(entity.getDireccion());
            dto.setCiudad(entity.getCiudad());
            dto.setPais(entity.getPais());
            dto.setTelefono(entity.getTelefono());
            dto.setEmail(entity.getEmail());
            dto.setWeb(entity.getWeb());
        }
        return dto;
    }

    public static List<EmpresaDto> from(List<Empresa> list) {
        List<EmpresaDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (Empresa entity : list) {
                dtos.add(EmpresaDto.from(entity));
            }
        }
        return dtos;
    }

    public Empresa to() {
        Empresa entity = new Empresa();
        entity.setId(id);
        entity.setNombre(nombre);
        entity.setCif(cif);
        entity.setDireccion(direccion);
        entity.setCiudad(ciudad);
        entity.setPais(pais);
        entity.setTelefono(telefono);
        entity.setEmail(email);
        entity.setWeb(web);
        return entity;
    }
}
