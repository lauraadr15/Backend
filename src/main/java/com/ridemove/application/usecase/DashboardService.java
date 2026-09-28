package com.ridemove.application.usecase;

import com.ridemove.domain.exception.UsuarioNoEncontradoException;
import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.in.ConsultarDashboardUseCase;
import com.ridemove.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;


@Service
public class DashboardService implements ConsultarDashboardUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public DashboardService(UsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public Usuario obtenerResumen(String correoUsuarioAutenticado) {
        return usuarioRepositoryPort.buscarPorCorreo(correoUsuarioAutenticado)
                .orElseThrow(() -> new UsuarioNoEncontradoException(correoUsuarioAutenticado));
    }
}
