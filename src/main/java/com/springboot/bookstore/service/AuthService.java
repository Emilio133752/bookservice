package com.springboot.bookstore.service;

import com.springboot.bookstore.domain.model.Usuario;
import com.springboot.bookstore.dto.auth.LoginRequestDTO;
import com.springboot.bookstore.dto.auth.LoginResponseDTO;
import com.springboot.bookstore.repository.UsuarioRepository;
import com.springboot.bookstore.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(
            loginRequest.getUsername(), 
            loginRequest.getPassword()
        );
        
        var auth = authenticationManager.authenticate(usernamePassword);
        
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        
        return new LoginResponseDTO(
            token, 
            loginRequest.getUsername(), 
            ((Usuario) auth.getPrincipal()).getRole().name()
        );
    }

    public Usuario register(Usuario usuario) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return usuarioRepository.save(usuario);
    }
}