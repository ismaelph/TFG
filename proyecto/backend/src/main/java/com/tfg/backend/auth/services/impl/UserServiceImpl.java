package com.tfg.backend.auth.services.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.auth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        User userBd = userRepository.findById(id);
        if(userBd != null) {
            userBd.setUsername(user.getUsername());
            userBd.setPassword(user.getPassword());
            userBd.setEmail(user.getEmail());
            userBd.setNif(user.getNif());
            userBd.setNombre(user.getNombre());
            userBd.setApellido1(user.getApellido1());
            userBd.setApellido2(user.getApellido2());
            userBd.setTelefono(user.getTelefono());
            userBd.setDireccion(user.getDireccion());
            userBd.setEmpresa(user.getEmpresa());
            return userRepository.save(userBd);
        }
        return userBd;
    }

    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByNif(String nif) {
        return userRepository.findByNif(nif);
    }
}
