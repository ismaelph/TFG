package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.repository.UsuarioRepository;
import com.tfg.backend.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public User save(User user) {
        return usuarioRepository.save(user);
    }

    @Override
    public User update(User user, long id) {
        User actual = findById(id);
        if (actual != null) {
            actual.setUsername(user.getUsername());
            actual.setEmail(user.getEmail());
            // No cambiamos password ni roles desde aquí
            return usuarioRepository.save(actual);
        }
        return null;
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
        usuarioRepository.save(user);
    }
}
