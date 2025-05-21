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
public class SolicitudMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    private Integer cantidadSolicitada;

    @Enumerated(EnumType.STRING)
    private EstadoSolicitud estado;

    private String motivo;

    @CreationTimestamp
    private Instant fechaSolicitud;

    private Instant fechaResolucion;

    private String respuestaAdmin; // motivo de rechazo o comentario
}
