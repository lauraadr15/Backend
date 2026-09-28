package com.ridemove.domain.port.out;

import com.ridemove.domain.model.Usuario;

import java.util.Optional;

/**
 * Puerto de salida (out): el dominio pide guardar/consultar usuarios sin saber
 * si detrás hay PostgreSQL, MongoDB o memoria. Se cumple DIP (Dependency Inversion).
 */
public interface UsuarioRepositoryPort {

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCorreo(String correo);

    boolean existePorCorreo(String correo);
}
