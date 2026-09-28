package com.ridemove.domain.port.out;

public interface PasswordEncoderPort {

    String cifrar(String contrasenaPlano);

    boolean coincide(String contrasenaPlano, String contrasenaCifrada);
}
