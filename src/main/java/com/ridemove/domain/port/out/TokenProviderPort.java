package com.ridemove.domain.port.out;

import com.ridemove.domain.model.Usuario;

public interface TokenProviderPort {

    String generarToken(Usuario usuario);

    /** @return el correo (subject) contenido en el token si es válido, o vacío si no lo es. */
    java.util.Optional<String> validarYExtraerCorreo(String token);
}
