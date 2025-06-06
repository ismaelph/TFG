package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);

    User update(User user, long id);

    User findById(long id);

    List<User> findAll();

    void delete(long id);

    void agregarUsuarioAEmpresa(User user, Empresa empresa);

    void actualizarRolYEmpresa(User user, Empresa empresa);

    void procesarUsuariosAntesDeEliminarEmpresa(Empresa empresa);

    void expulsarYDegradarUsuariosDeEmpresa(Empresa empresa);

    List<User> findByEmpresa(Empresa empresa);

    Optional<User> findByEmail(String email);





}
