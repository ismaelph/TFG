package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEmpresa(Empresa empresa);
}
