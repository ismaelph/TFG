package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Almacen;
import com.tfg.backend.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    List<Almacen> findByEmpresaId(Long empresaId);

    void deleteAllByEmpresa(Empresa empresa);

}