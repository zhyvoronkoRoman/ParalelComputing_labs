package com.edu.authServiceApplication.service;

import com.edu.authServiceApplication.client.WalletClient;
import com.edu.authServiceApplication.dto.AuthRequest;
import com.edu.authServiceApplication.dto.AuthResponse;
import com.edu.authServiceApplication.dto.RegisterRequest;
import com.edu.authServiceApplication.model.Role;
import com.edu.authServiceApplication.model.User;
import com.edu.authServiceApplication.repository.UserRepository;
import com.edu.authServiceApplication.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager manager;
    private final WalletClient client;

    public AuthResponse register(RegisterRequest request){
        var user = User.builder().username(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())).role(Role.USER)
                .build();
        userRepository.save(user);
        try {
            client.createWallet(user.getId());
        } catch (Exception e) {
            System.err.println("Excess to wallet denied\n" + e.getMessage());
        }

        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authorize(AuthRequest request){
        manager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(()->new UsernameNotFoundException("User not found") );
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}
