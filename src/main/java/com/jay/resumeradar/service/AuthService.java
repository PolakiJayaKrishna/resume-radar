package com.jay.resumeradar.service;


import com.jay.resumeradar.dto.AuthResponse;
import com.jay.resumeradar.dto.LoginRequest;
import com.jay.resumeradar.dto.RegisterRequest;

public interface AuthService {
    //For Register and Login. DTOs
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
