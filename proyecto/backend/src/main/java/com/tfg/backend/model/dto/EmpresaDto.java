package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.models.UserDto;
import com.tfg.backend.model.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmpresaDto {
    private Long id;
    private String nombre;
    private String password;
    private List<UserDto> admins;

    public static EmpresaDto from(Empresa entity) {
        EmpresaDto dto = new EmpresaDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPassword(entity.getPassword());
        dto.setAdmins(entity.getAdmins().stream().map(UserDto::from).toList());
        return dto;
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

    public Empresa to(){
        Empresa entity = new Empresa();
        entity.setId(this.getId());
        entity.setNombre(this.getNombre());
        entity.setPassword(this.getPassword());
        if (this.getAdmins() != null) {
            entity.setAdmins(this.getAdmins().stream().map(UserDto::to).collect(Collectors.toSet())); // Convertir los admins
        }
        return entity;
    }
}
