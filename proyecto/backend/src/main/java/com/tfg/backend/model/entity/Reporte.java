package com.tfg.backend.model.entity;

import com.tfg.backend.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;

    @Column(name = "nombre_reporte", nullable = false, length = 255)
    private String nombreReporte;

    @CreationTimestamp
    @Column(name = "fecha_generacion", updatable = false)
    private Instant fechaGeneracion;

    @Column(name = "archivo_pdf", nullable = true)
    private String archivoPdf;
}
