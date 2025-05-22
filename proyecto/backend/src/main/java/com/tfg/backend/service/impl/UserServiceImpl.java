package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.Role;
import com.tfg.backend.auth.models.RoleEnum;
import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.RoleRepository;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.UsuarioRepository;
import com.tfg.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;



    @Override
    public User save(User user) {
        return usuarioRepository.save(user);
    }

    @Override
    public User update(User userEditado, long id) {
        User userBD = findById(id);

        if (userEditado.getUsername() != null)
            userBD.setUsername(userEditado.getUsername());

        if (userEditado.getEmail() != null)
            userBD.setEmail(userEditado.getEmail());

        return userRepository.save(userBD);
    }


    @Override
    public User findById(long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public void delete(long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public void agregarUsuarioAEmpresa(User user, Empresa empresa) {
        user.setEmpresa(empresa);

        Role empleadoRol = roleRepository.findByName(RoleEnum.ROLE_EMPLEADO)
                .orElseThrow(() -> new RuntimeException("Rol ROLE_EMPLEADO no encontrado"));

        Set<Role> nuevosRoles = new HashSet<>();
        nuevosRoles.add(empleadoRol);
        user.setRoles(nuevosRoles);

        userRepository.save(user);
    }


    @Override
    public void actualizarRolYEmpresa(User user, Empresa empresa) {
        // Asignar empresa
        user.setEmpresa(empresa);

        // Cambiar rol a ADMIN_EMPRESA
        Role adminEmpresaRole = roleRepository
                .findByName(RoleEnum.ROLE_ADMIN_EMPRESA)
                .orElseThrow(() -> new RuntimeException("Error: Rol no encontrado."));

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        } else {
            user.getRoles().clear();
        }

        user.getRoles().add(adminEmpresaRole);

        usuarioRepository.save(user);
    }


    @Override
    public void procesarUsuariosAntesDeEliminarEmpresa(Empresa empresa) {
        List<User> usuarios = usuarioRepository.findByEmpresa(empresa);

        Role rolUser = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado"));

        for (User user : usuarios) {
            user.setEmpresa(null);
            user.getRoles().clear();
            user.getRoles().add(rolUser);
        }

        usuarioRepository.saveAll(usuarios);
    }

    @Override
    public void expulsarYDegradarUsuariosDeEmpresa(Empresa empresa) {
        List<User> usuarios = usuarioRepository.findByEmpresa(empresa);

        if (usuarios == null || usuarios.isEmpty()) return;

        Role rolUser = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado"));

        for (User user : usuarios) {
            user.setEmpresa(null);
            user.getRoles().clear();
            user.getRoles().add(rolUser);
        }

        usuarioRepository.saveAll(usuarios);

        empresa.setUsuarios(new ArrayList<>()); // evitar conflictos
    }

    @Override
    public List<User> findByEmpresa(Empresa empresa) {
        return usuarioRepository.findByEmpresa(empresa);
    }


}
