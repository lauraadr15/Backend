package com.ridemove.infrastructure.adapter.in.web.dto;

import com.ridemove.domain.model.Usuario;

public record RegistroResponse(Long id, String nombre, String correo, String rol) {
    public static RegistroResponse desde(Usuario usuario) {
        return new RegistroResponse(usuario.getId(), usuario.getNombre(), usuario.getCorreo(), usuario.getRol().name());
    }
}
