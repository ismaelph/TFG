package com.tfg.backend.model.repository;

import com.tfg.backend.auth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
