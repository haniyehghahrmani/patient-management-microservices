package com.hani.authservice.service;

import com.hani.authservice.dto.LoginRequestDTO;
import com.hani.authservice.dto.LoginResponseDTO;
import com.hani.authservice.model.RefreshToken;
import com.hani.authservice.model.User;
import com.hani.authservice.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    public Optional<LoginResponseDTO> authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<User> userOptional = userService.findByEmail(loginRequestDTO.getEmail());

        if (userOptional.isEmpty()){
            return Optional.empty();
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())){
            return Optional.empty();
        }

        String accessToken = jwtUtil.generateToken(user.getEmail(), user.getRole());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return Optional.of(
                new LoginResponseDTO(
                        accessToken,
                        refreshToken.getToken()
                )
        );
    }

    public boolean validateToken(String token){
        try {
            jwtUtil.validateToken(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }
}
