package com.jay.resumeradar.service;

import com.jay.resumeradar.dto.AuthResponse;
import com.jay.resumeradar.dto.LoginRequest;
import com.jay.resumeradar.dto.RegisterRequest;
import com.jay.resumeradar.entities.Role;
import com.jay.resumeradar.repository.UserRepository;
import com.jay.resumeradar.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import com.jay.resumeradar.entities.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {

        // 1. Build the User entity from the incoming request bucket
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))    // Scramble the password before saving!
                .role(Role.USER)                                             // Default role for new signups
                .createdAt(LocalDateTime.now())
                .build();

        // 2. Save the User to the MySQL database
        userRepository.save(user);

        // 3. Generate a JWT token badge for the new user
        var jwtToken = jwtService.generateToken(user);

        // 4. Return the token inside our AuthResponse bucket
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        //Does this person have the right key?
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );//If yes, proceed or throw an Error.

        //Now that I trust them, let me get their ID card from the DB.
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        //Now generate Token
        var jwtToken = jwtService.generateToken(user);
        //send the response
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
