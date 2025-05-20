package com.tfg.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estanteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // Ej: F1-E2

    @ManyToOne
    @JoinColumn(name = "planta_id")
    private Planta planta;
}
