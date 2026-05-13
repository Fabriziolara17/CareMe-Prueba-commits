package com.upc.careme.services;

import com.upc.careme.dtos.LoginRequestDTO;
import com.upc.careme.dtos.RegistroUsuarioDTO;
import com.upc.careme.dtos.UsuarioDTO;
import com.upc.careme.entidades.Usuario;
import com.upc.careme.repositorios.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UsuarioDTO login(LoginRequestDTO request) {

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos."));


        if (!passwordEncoder.matches(request.getPassword(), usuario.getPasswordHash())) {
            throw new IllegalArgumentException("Correo o contraseña incorrectos.");
        }


        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    public UsuarioDTO registrar(RegistroUsuarioDTO dto) {


        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Este correo ya está registrado en Care Me.");
        }


        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(dto.getEmail());
        nuevoUsuario.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        nuevoUsuario.setNombres(dto.getNombres());
        nuevoUsuario.setApellidos(dto.getApellidos());
        nuevoUsuario.setTelefono(dto.getTelefono());


        com.upc.careme.entidades.TipoUsuario tipo = new com.upc.careme.entidades.TipoUsuario();
        tipo.setIdTipo(dto.getIdTipo());
        nuevoUsuario.setTipoUsuario(tipo);


        nuevoUsuario.setRol(dto.getIdTipo() == 1 ? "familiar" : "cuidador");


        nuevoUsuario = usuarioRepository.save(nuevoUsuario);


        return modelMapper.map(nuevoUsuario, UsuarioDTO.class);
    }

    public String recuperarPassword(String email, String telefono) {
        Usuario usuario;

        if (email != null && !email.isBlank()) {
            usuario = usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("No existe una cuenta registrada con ese correo."));
        } else if (telefono != null && !telefono.isBlank()) {
            usuario = usuarioRepository.findByTelefono(telefono)
                    .orElseThrow(() -> new IllegalArgumentException("No existe una cuenta asociada a ese número de teléfono."));
        } else {
            throw new IllegalArgumentException("Debe proporcionar un correo electrónico o número de teléfono.");
        }

        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusHours(1));
        usuarioRepository.save(usuario);

        String destino = (email != null && !email.isBlank())
                ? "correo: " + email
                : "teléfono: " + telefono;

        return "Enlace de recuperación generado. Token: " + token +
                ". Válido por 1 hora. (Simulado — en producción se enviaría al " + destino + ")";
    }

    public String resetPassword(String token, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByTokenRecuperacion(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido o ya utilizado."));

        if (LocalDateTime.now().isAfter(usuario.getTokenExpiracion())) {
            throw new IllegalArgumentException("El token ha expirado. Solicita un nuevo enlace de recuperación.");
        }

        usuario.setPasswordHash(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);
        usuarioRepository.save(usuario);

        return "Contraseña actualizada correctamente. Ya puedes iniciar sesión con tu nueva contraseña.";
    }
}