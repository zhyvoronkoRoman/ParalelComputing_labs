package com.edu.cryptoTradingApp.service;


import com.edu.cryptoTradingApp.dto.request.AuthRequest;
import com.edu.cryptoTradingApp.dto.request.RegisterRequest;
import com.edu.cryptoTradingApp.dto.response.AuthResponse;
import com.edu.cryptoTradingApp.model.Role;
import com.edu.cryptoTradingApp.model.User;
import com.edu.cryptoTradingApp.repository.UserRepository;
import com.edu.cryptoTradingApp.utils.security.JwtService;
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
    private final UserService userService;

    public AuthResponse register(RegisterRequest request){
        var user = User.builder().username(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password())).role(Role.USER)
                .build();
        userRepository.save(user);
        userService.createWallet(user);
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
