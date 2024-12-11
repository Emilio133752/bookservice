package com.springboot.bookstore.controller;

import com.springboot.bookstore.domain.model.Usuario;
import com.springboot.bookstore.dto.auth.LoginRequestDTO;
import com.springboot.bookstore.dto.auth.LoginResponseDTO;
import com.springboot.bookstore.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
        @RequestBody @Valid LoginRequestDTO loginRequest
    ) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody @Valid Usuario usuario) {
        return ResponseEntity.ok(authService.register(usuario));
    }
}