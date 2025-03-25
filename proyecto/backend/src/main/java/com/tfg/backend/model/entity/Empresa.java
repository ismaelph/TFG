package com.tfg.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tfg.backend.auth.models.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(length = 100, nullable = false, unique = true)
    private String nombre;

    @NotBlank
    @Size(max = 14)
    @Column(length = 14, nullable = false, unique = true)
    private String cif; // Código de identificación fiscal

    @NotBlank
    @Size(max = 200)
    @Column(length = 200, nullable = false)
    private String direccion;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String ciudad;

    @NotBlank
    @Size(max = 50)
    @Column(length = 50, nullable = false)
    private String pais;

    @Column(nullable = false, unique = true)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(nullable = true)
    private String web;

    @JsonIgnore
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<User> users;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
