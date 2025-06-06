package com.tfg.backend.model.repository;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPersonalizadaRepository extends JpaRepository<SolicitudPersonalizada, Long> {
    List<SolicitudPersonalizada> findByUsuario(User usuario);
    List<SolicitudPersonalizada> findByUsuario_EmpresaAndLeidaFalse(Empresa empresa);
    List<SolicitudPersonalizada> findByUsuario_Empresa(Empresa empresa);


}
