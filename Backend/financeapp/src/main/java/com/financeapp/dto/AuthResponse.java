package com.financeapp.dto;

import com.financeapp.entity.User;

public class AuthResponse {
    
    private String token;
    private UserDTO user;
    
    // Constructeurs
    public AuthResponse() {
    }
    
    public AuthResponse(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
    
    // Getters et Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public UserDTO getUser() {
        return user;
    }
    
    public void setUser(UserDTO user) {
        this.user = user;
    }
    
    // Classe interne UserDTO
    public static class UserDTO {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private String role;  // CHANGÉ: String au lieu de User.Role
        private String status; // AJOUTÉ
        
        // Constructeur par défaut
        public UserDTO() {
        }
        
        // Getters et Setters
        public String getId() {
            return id;
        }
        
        public void setId(String id) {
            this.id = id;
        }
        
        public String getEmail() {
            return email;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        
        public String getRole() {
            return role;
        }
        
        public void setRole(String role) {
            this.role = role;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        // Méthode de conversion
        public static UserDTO fromUser(User user) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setRole(user.getRole().name());      
            dto.setStatus(user.getStatus().name());  
            return dto;
        }
    }
}