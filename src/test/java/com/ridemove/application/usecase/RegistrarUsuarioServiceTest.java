package com.ridemove.application.usecase;

import com.ridemove.domain.exception.UsuarioYaExisteException;
import com.ridemove.domain.model.Rol;
import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.out.PasswordEncoderPort;
import com.ridemove.domain.port.out.UsuarioRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Prueba unitaria pura: no levanta Spring ni base de datos.
 * Se simulan los puertos de salida (mocks), demostrando el desacople de la arquitectura hexagonal.
 */
@ExtendWith(MockitoExtension.class)
class RegistrarUsuarioServiceTest {

    @Mock
    private UsuarioRepositoryPort usuarioRepositoryPort;

    @Mock
    private PasswordEncoderPort passwordEncoderPort;

    @Test
    void deberiaRegistrarUsuarioCuandoCorreoNoExiste() {
        RegistrarUsuarioService service = new RegistrarUsuarioService(usuarioRepositoryPort, passwordEncoderPort);

        when(usuarioRepositoryPort.existePorCorreo("laura@ucundinamarca.edu.co")).thenReturn(false);
        when(passwordEncoderPort.cifrar("clave12345")).thenReturn("clave-cifrada");
        when(usuarioRepositoryPort.guardar(any(Usuario.class)))
                .thenAnswer(invocacion -> invocacion.getArgument(0));

        Usuario resultado = service.registrar(
                "Laura", "laura@ucundinamarca.edu.co", "clave12345", "3000000000", Rol.PASAJERO);

        assertThat(resultado.getCorreo()).isEqualTo("laura@ucundinamarca.edu.co");
        assertThat(resultado.getContrasenaCifrada()).isEqualTo("clave-cifrada");
        verify(usuarioRepositoryPort).guardar(any(Usuario.class));
    }

    @Test
    void deberiaLanzarExcepcionCuandoCorreoYaExiste() {
        RegistrarUsuarioService service = new RegistrarUsuarioService(usuarioRepositoryPort, passwordEncoderPort);

        when(usuarioRepositoryPort.existePorCorreo(anyString())).thenReturn(true);

        assertThatThrownBy(() ->
                service.registrar("Laura", "laura@ucundinamarca.edu.co", "clave12345", null, Rol.PASAJERO)
        ).isInstanceOf(UsuarioYaExisteException.class);

        verify(usuarioRepositoryPort, never()).guardar(any());
    }
}
