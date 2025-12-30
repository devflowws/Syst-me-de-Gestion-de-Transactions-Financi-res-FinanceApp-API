package com.financeapp.service.interfaces;  

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    AuthResponse register(AuthRequest request);
    AuthResponse authenticate(AuthRequest request);
    User getCurrentUser();
}