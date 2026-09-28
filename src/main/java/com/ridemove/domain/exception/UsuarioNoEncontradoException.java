package com.ridemove.domain.exception;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String correo) {
        super("No se encontró un usuario con el correo: " + correo);
    }
}
