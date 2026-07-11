package com.hani.authservice.service;

import com.hani.authservice.model.RefreshToken;
import com.hani.authservice.model.User;
import com.hani.authservice.repository.RefreshTokenRepository;
import com.hani.authservice.util.JwtUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtUtil jwtUtil;

    public RefreshTokenService(RefreshTokenRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(jwtUtil.generateRefreshToken(user.getEmail()));
        refreshToken.setExpiresAt(LocalDateTime.now().plusDays(7));
        refreshToken.setRevoked(false);
        refreshToken.setUser(user);
        return repository.save(refreshToken);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return repository.findByToken(token);
    }

    public boolean validateRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.isRevoked()) {
            return false;
        }
        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }
        return true;
    }

    public void revokeRefreshToken(RefreshToken refreshToken) {
        refreshToken.setRevoked(true);
        repository.save(refreshToken);
    }

}
