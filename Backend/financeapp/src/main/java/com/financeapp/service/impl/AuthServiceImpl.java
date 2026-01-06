package com.financeapp.service.impl;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.entity.User;
import com.financeapp.repository.UserRepository;
import com.financeapp.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    
    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()
            )
        );
        
        // Définir l'authentification dans le contexte
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Récupérer l'utilisateur
        User user = userRepository.findByEmail(authRequest.getEmail())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Générer le token JWT
        String token = jwtUtil.generateToken(user.getEmail());
        
        // Créer la réponse
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        
        // Créer le UserDTO
        AuthResponse.UserDTO userDTO = new AuthResponse.UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRole(user.getRole().name());  
        userDTO.setStatus(user.getStatus().name()); 
        
        response.setUser(userDTO);
        
        return response;
    }
    
    @Override
    public void logout(String token) {
        // token invalidation can be handled here if token storage is implemented
        SecurityContextHolder.clearContext();
    }
    
    @Override
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }
}