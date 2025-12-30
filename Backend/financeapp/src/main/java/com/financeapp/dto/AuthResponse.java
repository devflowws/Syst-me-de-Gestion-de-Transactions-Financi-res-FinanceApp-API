package com.financeapp.dto;

import com.financeapp.entity.User;

public class AuthResponse {
    
    private String token;
    private UserDTO user;
    
    public AuthResponse(String token2, String string, String id, String email) {
        //TODO Auto-generated constructor stub
    }

    public AuthResponse() {
        //TODO Auto-generated constructor stub
    }

    // Getters et Setters pour AuthResponse
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
        private User.Role role;
        
        // Getters et Setters pour UserDTO
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
        
        public User.Role getRole() {
            return role;
        }
        
        public void setRole(User.Role role) {
            this.role = role;
        }
        
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

    public void setTokenType(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setTokenType'");
    }

    public void setUserId(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUserId'");
    }

    public void setEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setEmail'");
    }
}