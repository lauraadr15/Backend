package com.ridemove.domain.port.in;

public interface AutenticarUsuarioUseCase {

    /** @return el token JWT firmado, listo para devolver al cliente. */
    String autenticar(String correo, String contrasenaPlano);
}
