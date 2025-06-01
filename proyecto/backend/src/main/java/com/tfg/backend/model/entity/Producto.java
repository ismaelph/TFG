package com.tfg.backend.model.entity;

import com.tfg.backend.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private BigDecimal precio;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private boolean usoInterno;

    @CreationTimestamp
    private LocalDateTime fechaIngreso;

    @Column(nullable = true)
    private Integer stockMinimo;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToOne
    @JoinColumn(name = "estanteria_id")
    private Estanteria estanteria;

    @Column(name = "imagen_url")
    private String imagenUrl;


}
