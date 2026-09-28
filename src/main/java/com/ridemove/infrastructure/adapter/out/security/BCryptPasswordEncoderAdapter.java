package com.ridemove.infrastructure.adapter.out.security;

import com.ridemove.domain.port.out.PasswordEncoderPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptPasswordEncoderAdapter implements PasswordEncoderPort {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String cifrar(String contrasenaPlano) {
        return encoder.encode(contrasenaPlano);
    }

    @Override
    public boolean coincide(String contrasenaPlano, String contrasenaCifrada) {
        return encoder.matches(contrasenaPlano, contrasenaCifrada);
    }
}
