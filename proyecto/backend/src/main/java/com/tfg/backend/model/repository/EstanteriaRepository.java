package com.tfg.backend.model.repository;


import com.tfg.backend.model.entity.Estanteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstanteriaRepository extends JpaRepository<Estanteria, Long> {
    List<Estanteria> findByPlantaId(Long plantaId);
}