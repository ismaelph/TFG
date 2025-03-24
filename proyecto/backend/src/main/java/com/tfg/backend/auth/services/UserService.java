package com.tfg.backend.auth.services;

import com.tfg.backend.auth.models.User;

import java.util.List;

public interface UserService {
    User save(User user);
    User update(User user, long id);
    void delete(long id);

    User findById(long id);
    List<User> findAll();
    User findByNif(String nif);
}
