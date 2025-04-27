package com.tfg.backend.auth.models;

import com.tfg.backend.model.dto.EmpresaDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private EmpresaDto empresa;
    private String fotoPerfil;

    public static UserDto from(User entity) {
        UserDto dto = null;
        if (entity != null) {
            dto = new UserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setPassword(entity.getPassword());
            dto.setEmail(entity.getEmail());
            dto.setFotoPerfil(entity.getFotoPerfil());
            dto.setEmpresa(entity.getEmpresa() != null ? EmpresaDto.from(entity.getEmpresa()) : null);
        }
        return dto;
    }

    public static List<UserDto> from(List<User> list) {
        List<UserDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<UserDto>();
            for (User entity : list) {
                dtos.add(UserDto.from(entity));
            }
        }
        return dtos;
    }

    public User to() {
        User entity = new User();
        entity.setId(this.getId());
        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setEmail(this.getEmail());
        entity.setFotoPerfil(this.getFotoPerfil());
        entity.setEmpresa(this.empresa != null ? this.empresa.to() : null);
        return entity;
    }
}
