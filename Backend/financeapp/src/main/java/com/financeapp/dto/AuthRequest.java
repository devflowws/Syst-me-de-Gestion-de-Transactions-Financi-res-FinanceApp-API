package com.financeapp.dto;

import com.financeapp.entity.User;

public class AuthRequest {
    
    private String token;
    private UserDTO user;
    
    // Constructeur
    public AuthRequest() {
    }
    
    public AuthRequest(String token, UserDTO user) {
        this.token = token;
        this.user = user;
    }
    
    // Getters
    public String getToken() {
        return token;
    }
    
    public UserDTO getUser() {
        return user;
    }
    
    // Setters
    public void setToken(String token) {
        this.token = token;
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
        private User.Role role;
        
        // Constructeur
        public UserDTO() {
        }
        
        public UserDTO(String id, String email, String firstName, String lastName, User.Role role) {
            this.id = id;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
            this.role = role;
        }
        
        // Getters
        public String getId() {
            return id;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public User.Role getRole() {
            return role;
        }
        
        // Setters
        public void setId(String id) {
            this.id = id;
        }
        
        public void setEmail(String email) {
            this.email = email;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        
        public void setRole(User.Role role) {
            this.role = role;
        }
        
        // Méthode de conversion
        public static UserDTO fromUser(User user) {
            UserDTO dto = new UserDTO();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setRole(user.getRole());
            return dto;
        }
    }

    public String getEmail() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEmail'");
    }

    public CharSequence getPassword() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
    }
}