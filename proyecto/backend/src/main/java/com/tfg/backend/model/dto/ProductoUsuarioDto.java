package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.dto.UserDto;
import com.tfg.backend.model.entity.ProductoUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductoUsuarioDto {
    private Long id;
    private ProductoDto producto;
    private UserDto usuario;

    public static ProductoUsuarioDto from(ProductoUsuario entity) {
        ProductoUsuarioDto dto = null;
        if (entity != null) {
            dto = new ProductoUsuarioDto();
            dto.setId(entity.getId());
            dto.setProducto(ProductoDto.from(entity.getProducto()));
            dto.setUsuario(UserDto.from(entity.getUsuario()));
        }
        return dto;
    }

    public static List<ProductoUsuarioDto> from(List<ProductoUsuario> list) {
        List<ProductoUsuarioDto> dtos = null;
        if (list != null) {
            dtos = new ArrayList<>();
            for (ProductoUsuario entity : list) {
                dtos.add(ProductoUsuarioDto.from(entity));
            }
        }
        return dtos;
    }

    public ProductoUsuario to() {
        ProductoUsuario entity = new ProductoUsuario();
        entity.setId(id);
        entity.setProducto(producto.to());
        entity.setUsuario(usuario.to());
        return entity;
    }
}
