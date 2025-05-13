package com.tfg.backend.model.repository;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.MovimientoProducto;
import com.tfg.backend.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, Long> {

    List<MovimientoProducto> findByEmpresa(Empresa empresa);

    List<MovimientoProducto> findByProducto(Producto producto);

    List<MovimientoProducto> findByUsuario(User usuario);
}
