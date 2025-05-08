package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.models.Role;
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
    private String email;
    private String rol;

    public static UserDto from(User entity) {
        UserDto dto = new UserDto();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());

        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            dto.setRol(entity.getRoles().iterator().next().getName().name());
        }

        return dto;
    }

    public User to() {
        User entity = new User();
        entity.setId(this.getId());
        entity.setUsername(this.getUsername());
        entity.setEmail(this.getEmail());
        return entity;
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
}
