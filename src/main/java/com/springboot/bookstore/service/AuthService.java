package com.springboot.bookstore.service;

import com.springboot.bookstore.domain.model.Usuario;
import com.springboot.bookstore.domain.enums.Role;
import com.springboot.bookstore.dto.auth.LoginRequestDTO;
import com.springboot.bookstore.dto.auth.LoginResponseDTO;
import com.springboot.bookstore.dto.auth.RegisterRequestDTO;
import com.springboot.bookstore.dto.auth.RegisterResponseDTO;
import com.springboot.bookstore.repository.UsuarioRepository;
import com.springboot.bookstore.security.TokenService;
import org.springframework.security.core.Authentication; // Add this import
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

    public RegisterResponseDTO register(RegisterRequestDTO registerRequest) {
        // Verificar se usuário já existe
        if (usuarioRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Usuário já existe");
        }

        // Criar novo usuário
        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsername(registerRequest.getUsername());
        novoUsuario.setEmail(registerRequest.getEmail());
        novoUsuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        novoUsuario.setRole(registerRequest.getRole()); // Isso deve funcionar agora

        // Salvar usuário
        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        // Gerar token
        String token = tokenService.generateToken(usuarioSalvo);

        // Retornar resposta de registro
        return new RegisterResponseDTO(
                usuarioSalvo.getId(),
                usuarioSalvo.getUsername(),
                token,
                usuarioSalvo.getRole()
        );
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            // Criar token de autenticação
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            // Autenticar
            Authentication auth = authenticationManager.authenticate(usernamePassword);

            // Encontrar usuário
            Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            // Gerar novo token
            String novoToken = tokenService.generateToken(usuario);

            // Retornar resposta de login
            return new LoginResponseDTO(
                    novoToken,
                    usuario.getUsername(),
                    usuario.getRole()
            );
        } catch (Exception e) {
            throw new RuntimeException("Falha na autenticação", e);
        }
    }
}