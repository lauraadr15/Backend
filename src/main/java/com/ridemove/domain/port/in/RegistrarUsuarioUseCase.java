package com.ridemove.domain.port.in;

import com.ridemove.domain.model.Rol;
import com.ridemove.domain.model.Usuario;

/**
 * Puerto de entrada (in): define QUÉ se puede hacer, no CÓMO.
 * Los adaptadores web (controllers) dependen de esta interfaz, nunca de la implementación.
 */
public interface RegistrarUsuarioUseCase {

    Usuario registrar(String nombre, String correo, String contrasenaPlano, String telefono, Rol rol);
}
