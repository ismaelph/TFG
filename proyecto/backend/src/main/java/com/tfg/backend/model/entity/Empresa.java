package com.tfg.backend.model.entity;

import com.tfg.backend.auth.models.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "empresa_admins", joinColumns = @JoinColumn(name = "empresa_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> admins = new HashSet<>();

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private Instant createdAt;
    @UpdateTimestamp
    private Instant updatedAt;
}
