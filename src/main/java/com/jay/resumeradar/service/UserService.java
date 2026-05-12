package com.jay.resumeradar.service;

import com.jay.resumeradar.entities.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    Optional<User> getUserByEmail(String email);
}
