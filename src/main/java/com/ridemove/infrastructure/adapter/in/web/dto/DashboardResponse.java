package com.ridemove.infrastructure.adapter.in.web.dto;

import com.ridemove.domain.model.Usuario;

public record DashboardResponse(String nombre, String correo, String rol, String mensajeBienvenida) {
    public static DashboardResponse desde(Usuario usuario) {
        return new DashboardResponse(
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol().name(),
                "Bienvenido a RideMove, " + usuario.getNombre()
        );
    }
}
