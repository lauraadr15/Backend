package com.ridemove.application.usecase;

import com.ridemove.domain.exception.CredencialesInvalidasException;
import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.in.AutenticarUsuarioUseCase;
import com.ridemove.domain.port.out.PasswordEncoderPort;
import com.ridemove.domain.port.out.TokenProviderPort;
import com.ridemove.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AutenticarUsuarioService implements AutenticarUsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenProviderPort tokenProviderPort;

    public AutenticarUsuarioService(UsuarioRepositoryPort usuarioRepositoryPort,
                                     PasswordEncoderPort passwordEncoderPort,
                                     TokenProviderPort tokenProviderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public String autenticar(String correo, String contrasenaPlano) {
        Usuario usuario = usuarioRepositoryPort.buscarPorCorreo(correo)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoderPort.coincide(contrasenaPlano, usuario.getContrasenaCifrada())) {
            throw new CredencialesInvalidasException();
        }

        return tokenProviderPort.generarToken(usuario);
    }
}
