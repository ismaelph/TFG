package com.tfg.backend.model.entity;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.enums.EstadoSolicitud;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitudPersonalizada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreProductoSugerido;

    private String descripcion;

    private Integer cantidadDeseada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    private String respuestaAdmin;

    @CreationTimestamp
    private Instant fechaSolicitud;

    private Instant fechaResolucion;

    @Column(nullable = false)
    private boolean leida = false;

    @Column(name = "leida_empleado", nullable = false)
    private boolean leidaEmpleado = false;

}
