package com.ridemove.infrastructure.adapter.in.web;

import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.in.ConsultarDashboardUseCase;
import com.ridemove.infrastructure.adapter.in.web.dto.DashboardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Funcionalidad 3: Dashboard. Requiere JWT válido (ver SecurityConfig).
 * El correo del usuario autenticado llega desde JwtAuthenticationFilter -> SecurityContext.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final ConsultarDashboardUseCase consultarDashboardUseCase;

    public DashboardController(ConsultarDashboardUseCase consultarDashboardUseCase) {
        this.consultarDashboardUseCase = consultarDashboardUseCase;
    }

    @GetMapping
    public ResponseEntity<DashboardResponse> obtenerDashboard(Authentication authentication) {
        String correoAutenticado = authentication.getName();
        Usuario usuario = consultarDashboardUseCase.obtenerResumen(correoAutenticado);
        return ResponseEntity.ok(DashboardResponse.desde(usuario));
    }
}
