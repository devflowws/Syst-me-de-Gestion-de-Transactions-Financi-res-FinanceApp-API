package com.financeapp.service.impl;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    void logout(String token);
    boolean validateToken(String token);
}