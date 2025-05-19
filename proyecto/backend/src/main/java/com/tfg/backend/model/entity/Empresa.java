package com.tfg.backend.model.entity;

import com.tfg.backend.auth.models.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String claveAcceso;

    @CreationTimestamp
    private Instant fechaCreacion;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<User> usuarios = new ArrayList<>();

}
