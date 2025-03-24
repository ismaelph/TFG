package com.tfg.backend.auth.models.dto;

import com.tfg.backend.auth.models.Role;
import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Set<Role> roles = new HashSet<>();
    private String nif;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String direccion;
    private Empresa empresa;

    public static UserDto from(User entity) {
        UserDto dto = null;
        if (entity != null) {
            dto = new UserDto();
            dto.setId(entity.getId());
            dto.setUsername(entity.getUsername());
            dto.setPassword(entity.getPassword());
            dto.setEmail(entity.getEmail());
            dto.setNif(entity.getNif());
            dto.setNombre(entity.getNombre());
            dto.setApellido1(entity.getApellido1());
            dto.setApellido2(entity.getApellido2());
            dto.setTelefono(entity.getTelefono());
            dto.setDireccion(entity.getDireccion());
            dto.setRoles(entity.getRoles());
            dto.setEmpresa(entity.getEmpresa());
        }
        return dto;
    }

    public static List<UserDto> from(List<User> list) {
        List<UserDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (User entity : list) {
                dtos.add(UserDto.from(entity));
            }
        }
        return dtos;
    }

    public User to() {
        User entity = new User();
        entity.setId(id);
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setNif(nif);
        entity.setNombre(nombre);
        entity.setApellido1(apellido1);
        entity.setApellido2(apellido2);
        entity.setTelefono(telefono);
        entity.setDireccion(direccion);
        entity.setEmpresa(empresa);
        entity.setRoles(roles);
        return entity;
    }
}
