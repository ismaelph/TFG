package com.tfg.backend.auth.services.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.auth.services.UserService;
import com.tfg.backend.model.entity.Empresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user, long id) {
        Optional<User> optionalUserBd = userRepository.findById(id);

        if (optionalUserBd.isPresent()) {
            User userBd = optionalUserBd.get();
            userBd.setUsername(user.getUsername());
            userBd.setPassword(user.getPassword());
            userBd.setEmail(user.getEmail());
            userBd.setEmpresa(user.getEmpresa());
            userBd.setFotoPerfil(user.getFotoPerfil());
            return userRepository.save(userBd);
        }

        return null;
    }


    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void agregarUsuarioAEmpresa(User user, Empresa empresa) {
        user.setEmpresa(empresa);
        userRepository.save(user);
    }

    @Override
    public User obtenerUsuarioActual() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username));
    }
}
