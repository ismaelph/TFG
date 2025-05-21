package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ProductoDto {

    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer cantidad;
    private boolean usoInterno;
    private LocalDateTime fechaIngreso;
    private Integer stockMinimo;

    private Long empresaId;
    private Long categoriaId;
    private Long proveedorId;
    private Long usuarioId;
    private Long estanteriaId;

    private String categoriaNombre;
    private String proveedorNombre;
    private String ubicacion;

    public static ProductoDto from(Producto entity) {
        ProductoDto dto = new ProductoDto();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setCantidad(entity.getCantidad());
        dto.setUsoInterno(entity.isUsoInterno());
        dto.setFechaIngreso(entity.getFechaIngreso());
        dto.setStockMinimo(entity.getStockMinimo());

        if (entity.getEmpresa() != null)
            dto.setEmpresaId(entity.getEmpresa().getId());

        if (entity.getCategoria() != null) {
            dto.setCategoriaId(entity.getCategoria().getId());
            dto.setCategoriaNombre(entity.getCategoria().getNombre());
        }

        if (entity.getProveedor() != null) {
            dto.setProveedorId(entity.getProveedor().getId());
            dto.setProveedorNombre(entity.getProveedor().getNombre());
        }

        if (entity.getUsuario() != null)
            dto.setUsuarioId(entity.getUsuario().getId());

        if (entity.getEstanteria() != null) {
            Estanteria est = entity.getEstanteria();
            dto.setEstanteriaId(est.getId()); // ✅ Esto va lo primero, antes de nada

            Planta pla = est.getPlanta();
            Almacen alm = (pla != null) ? pla.getAlmacen() : null;

            StringBuilder ubicacion = new StringBuilder();

            if (alm != null && alm.getNombre() != null) {
                ubicacion.append(alm.getNombre()).append(" → ");
            }

            if (pla != null && pla.getNombre() != null) {
                ubicacion.append(pla.getNombre()).append(" → ");
            }

            if (est.getCodigo() != null) {
                ubicacion.append(est.getCodigo());
            }

            String ubicacionFinal = ubicacion.isEmpty() ? null : ubicacion.toString();
            dto.setUbicacion(ubicacionFinal);

            // ✅ Consola y logs para debug
            System.out.println("✅ Estantería encontrada: ID=" + est.getId());
            System.out.println("✅ Ubicación generada: " + ubicacionFinal);

            if (log != null) {
                log.info("✅ Estantería ID: {}", est.getId());
                log.info("✅ Ubicación final construida: {}", ubicacionFinal);
            }
        }



        return dto;
    }

    public Producto to() {
        Producto producto = new Producto();
        producto.setId(this.getId());
        producto.setNombre(this.getNombre());
        producto.setPrecio(this.getPrecio());
        producto.setCantidad(this.getCantidad());
        producto.setUsoInterno(this.isUsoInterno());
        producto.setFechaIngreso(this.getFechaIngreso());
        producto.setStockMinimo(this.getStockMinimo());

        if (this.empresaId != null) {
            Empresa empresa = new Empresa();
            empresa.setId(this.empresaId);
            producto.setEmpresa(empresa);
        }

        if (this.categoriaId != null) {
            Categoria categoria = new Categoria();
            categoria.setId(this.categoriaId);
            producto.setCategoria(categoria);
        }

        if (this.proveedorId != null) {
            Proveedor proveedor = new Proveedor();
            proveedor.setId(this.proveedorId);
            producto.setProveedor(proveedor);
        }

        if (this.usuarioId != null) {
            User usuario = new User();
            usuario.setId(this.usuarioId);
            producto.setUsuario(usuario);
        }

        if (this.estanteriaId != null) {
            Estanteria estanteria = new Estanteria();
            estanteria.setId(this.estanteriaId);
            producto.setEstanteria(estanteria);
        }

        return producto;
    }

    public static List<ProductoDto> from(List<Producto> list) {
        List<ProductoDto> dtos = new ArrayList<>();
        if (list != null) {
            for (Producto entity : list) {
                dtos.add(ProductoDto.from(entity));
            }
        }
        return dtos;
    }
}