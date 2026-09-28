package com.ridemove.domain.port.in;

import com.ridemove.domain.model.Usuario;

public interface ConsultarDashboardUseCase {

    /** Datos resumen que ve el usuario autenticado al entrar a la app. */
    Usuario obtenerResumen(String correoUsuarioAutenticado);
}
