package com.financeapp.service.impl;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.entity.Account;
import com.financeapp.entity.User;
import com.financeapp.repository.AccountRepository;
import com.financeapp.repository.UserRepository;
import com.financeapp.security.JwtService;
import com.financeapp.service.interfaces.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;  
    
    public UserServiceImpl(
            UserRepository userRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            @Lazy AuthenticationManager authenticationManager) {  
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }
    
    @Override
    @Transactional
    public AuthResponse register(AuthRequest request) {
        // Vérifier si l'email existe
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }
        
        try {
            // Créer l'utilisateur
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setRole(User.Role.ROLE_USER);
            user.setStatus(User.UserStatus.ACTIVE);
            
            User savedUser = userRepository.save(user);
            
            // Créer compte par défaut - le numéro sera généré automatiquement par @PrePersist
            Account defaultAccount = new Account();
            defaultAccount.setUser(savedUser);
            defaultAccount.setBalance(new BigDecimal("1000.00"));
            defaultAccount.setType(Account.AccountType.CHECKING);
            defaultAccount.setAccountName("Main Checking Account");
            accountRepository.save(defaultAccount);
            
            // Générer token JWT
            String jwtToken = jwtService.generateToken(user);
            
            // Créer et retourner la réponse
            AuthResponse response = new AuthResponse();
            response.setToken(jwtToken);
            response.setUser(AuthResponse.UserDTO.fromUser(savedUser));
            
            return response;
            
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Registration failed: " + e.getMessage());
        }
    }
    
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            // Authentification avec AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
            );
            
            // Récupérer l'utilisateur
            User user = (User) authentication.getPrincipal();
            
            // Mettre à jour dernière connexion
            user.setLastLogin(java.time.LocalDateTime.now());
            userRepository.save(user);
            
            // Générer token JWT
            String jwtToken = jwtService.generateToken(user);
            
            // Créer et retourner la réponse
            AuthResponse response = new AuthResponse();
            response.setToken(jwtToken);
            response.setUser(AuthResponse.UserDTO.fromUser(user));
            
            return response;
            
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }
    
    @Override
    public User getCurrentUser() {
        try {
            // Récupérer l'authentification du contexte
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No authenticated user");
            }
            
            // Récupérer l'email depuis l'authentification
            String email = authentication.getName();
            
            // Chercher l'utilisateur dans la base de données
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                    
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication failed");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}