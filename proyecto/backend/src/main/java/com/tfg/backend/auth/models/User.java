package com.tfg.backend.auth.models;

import com.tfg.backend.model.entity.Empresa;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "users",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = "username"),
      @UniqueConstraint(columnNames = "email")
    })
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public User (String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }

  @NotBlank
  @Size(max = 20)
  @Column(length = 50, unique = true)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  @Column(length = 150, unique = true)
  private String email;

  @NotBlank
  @Size(max = 120)
  @Column(length = 60)
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();
  @CreationTimestamp
  private Instant createdAt;
  @UpdateTimestamp
  private Instant updatedAt;

  @Column(unique = true, nullable = false, length = 12)
  private String nif;

  @Column(nullable = false, length = 50)
  private String nombre;

  @Column(nullable = false, length = 50)
  private String apellido1;

  @Column(nullable = false, length = 50)
  private String apellido2;

  @Column(nullable = false, unique = true)
  private int telefono;

  @Column(nullable = true)
  private String direccion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "empresa_id", nullable = true)
  private Empresa empresa;

}
