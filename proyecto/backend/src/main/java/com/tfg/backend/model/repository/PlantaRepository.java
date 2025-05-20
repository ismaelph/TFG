package com.tfg.backend.model.repository;


import com.tfg.backend.model.entity.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {
    List<Planta> findByAlmacenId(Long almacenId);
}