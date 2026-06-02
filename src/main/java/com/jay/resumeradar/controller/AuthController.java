package com.jay.resumeradar.controller;

import com.jay.resumeradar.dto.AuthResponse;
import com.jay.resumeradar.dto.LoginRequest;
import com.jay.resumeradar.dto.RegisterRequest;
import com.jay.resumeradar.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Controller", description = "Endpoints for user registration and JWT login")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Email already exists or invalid input")
    })
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        var response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Login an existing user", description = "Authenticates credentials and returns a JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated, JWT token returned"),
            @ApiResponse(responseCode = "401", description = "Bad credentials (Invalid email or password)")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        var response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
}
