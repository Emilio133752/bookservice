package com.springboot.bookstore.dto.auth;

import com.springboot.bookstore.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {
    private Long id;
    private String username;
    private String token;
    private Role role = Role.USER;  // Altere para Role
}