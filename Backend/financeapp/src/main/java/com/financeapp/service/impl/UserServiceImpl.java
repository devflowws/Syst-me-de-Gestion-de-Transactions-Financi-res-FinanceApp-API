package com.financeapp.service.impl;

import com.financeapp.dto.AuthRequest;
import com.financeapp.dto.AuthResponse;
import com.financeapp.entity.Account;
import com.financeapp.entity.User;
import com.financeapp.repository.AccountRepository;
import com.financeapp.repository.UserRepository;
import com.financeapp.security.JwtService;
import com.financeapp.service.interfaces.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
            @org.springframework.context.annotation.Lazy AuthenticationManager authenticationManager) {
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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà enregistré");
        }
        
        try {
            // Créer l'utilisateur
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            
            String email = request.getEmail();
            String firstName = email.split("@")[0];
            user.setFirstName(firstName);
            user.setLastName("User");
            user.setCreatedAt(LocalDateTime.now());
            
            User savedUser = userRepository.save(user);
            
            // Créer compte par défaut
            Account defaultAccount = new Account();
            defaultAccount.setUser(savedUser);
            defaultAccount.setBalance(new BigDecimal("1000.00"));
            defaultAccount.setType(Account.AccountType.CHECKING);
            accountRepository.save(defaultAccount);
            
            // Générer token
            String jwtToken = jwtService.generateToken(user);
            
            // Retourner réponse
            AuthResponse response = new AuthResponse();
            response.setToken(jwtToken);
            response.setUser(AuthResponse.UserDTO.fromUser(savedUser));
            
            return response;
            
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email déjà utilisé");
        }
    }
    
    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            // Remplacer authenticationManager par vérification manuelle TEMPORAIREMENT
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
            
            // Vérifier le mot de passe manuellement
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Email ou mot de passe incorrect");
            }
            
            // Mettre à jour dernière connexion
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);
            
            // Générer token
            String jwtToken = jwtService.generateToken(user);
            
            // Retourner réponse
            AuthResponse response = new AuthResponse();
            response.setToken(jwtToken);
            response.setUser(AuthResponse.UserDTO.fromUser(user));
            
            return response;
            
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou mot de passe incorrect");
        } catch (UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    @Override
    public User getCurrentUser() {
        try {
            // Récupérer email de l'utilisateur connecté
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Aucun utilisateur connecté");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));
    }
}