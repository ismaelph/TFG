package com.tfg.backend.model.repository;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudPersonalizadaRepository extends JpaRepository<SolicitudPersonalizada, Long> {
    List<SolicitudPersonalizada> findByUsuario(User usuario);
}
