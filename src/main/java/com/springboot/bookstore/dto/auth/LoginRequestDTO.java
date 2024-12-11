package com.springboot.bookstore.dto.auth;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    private String token;
    private String username;
    private String password;

    // Construtores
    public LoginRequestDTO() {}

    public LoginRequestDTO(String token, String username, String password) {
        this.token = token;
        this.username = username;
        this.password = password;
    }

    // Getters e Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}