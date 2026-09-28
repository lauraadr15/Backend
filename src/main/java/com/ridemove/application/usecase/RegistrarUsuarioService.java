package com.ridemove.application.usecase;

import com.ridemove.domain.exception.UsuarioYaExisteException;
import com.ridemove.domain.model.Rol;
import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.in.RegistrarUsuarioUseCase;
import com.ridemove.domain.port.out.PasswordEncoderPort;
import com.ridemove.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: registrar un usuario nuevo (pasajero o conductor).
 * Depende únicamente de puertos (interfaces), nunca de JPA ni de Spring Security directamente.
 */
@Service
public class RegistrarUsuarioService implements RegistrarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public RegistrarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                    PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public Usuario registrar(String nombre, String correo, String contrasenaPlano, String telefono, Rol rol) {
        if (usuarioRepositoryPort.existePorCorreo(correo)) {
            throw new UsuarioYaExisteException(correo);
        }

        String contrasenaCifrada = passwordEncoderPort.cifrar(contrasenaPlano);
        Usuario nuevoUsuario = Usuario.nuevo(nombre, correo, contrasenaCifrada, telefono, rol);

        return usuarioRepositoryPort.guardar(nuevoUsuario);
    }
}
