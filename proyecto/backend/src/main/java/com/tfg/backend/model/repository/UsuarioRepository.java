package com.tfg.backend.model.repository;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByEmpresa(Empresa empresa);

}
