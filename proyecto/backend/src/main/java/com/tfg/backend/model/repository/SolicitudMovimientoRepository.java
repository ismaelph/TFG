package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudMovimientoRepository extends JpaRepository<SolicitudMovimiento, Long> {
    List<SolicitudMovimiento> findByUsuario(User usuario);
}
