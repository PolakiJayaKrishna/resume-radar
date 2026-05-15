package com.jay.resumeradar.controller;

import com.jay.resumeradar.dto.AuthResponse;
import com.jay.resumeradar.dto.LoginRequest;
import com.jay.resumeradar.dto.RegisterRequest;
import com.jay.resumeradar.entities.User;
import com.jay.resumeradar.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        var response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
