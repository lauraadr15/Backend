package com.ridemove.infrastructure.adapter.in.web;

import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.in.AutenticarUsuarioUseCase;
import com.ridemove.domain.port.in.RegistrarUsuarioUseCase;
import com.ridemove.infrastructure.adapter.in.web.dto.LoginRequest;
import com.ridemove.infrastructure.adapter.in.web.dto.LoginResponse;
import com.ridemove.infrastructure.adapter.in.web.dto.RegistroRequest;
import com.ridemove.infrastructure.adapter.in.web.dto.RegistroResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador de entrada (web). Solo traduce HTTP <-> casos de uso; no contiene lógica de negocio.
 * Funcionalidad 1: Registro (RF-01) | Funcionalidad 2: Login (requisito de seguridad JWT).
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegistrarUsuarioUseCase registrarUsuarioUseCase;
    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(RegistrarUsuarioUseCase registrarUsuarioUseCase,
                           AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.registrarUsuarioUseCase = registrarUsuarioUseCase;
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    @PostMapping("/registro")
    public ResponseEntity<RegistroResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        Usuario usuario = registrarUsuarioUseCase.registrar(
                request.nombre(), request.correo(), request.contrasena(), request.telefono(), request.rol());

        return ResponseEntity.status(HttpStatus.CREATED).body(RegistroResponse.desde(usuario));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = autenticarUsuarioUseCase.autenticar(request.correo(), request.contrasena());
        return ResponseEntity.ok(LoginResponse.deToken(token));
    }
}
