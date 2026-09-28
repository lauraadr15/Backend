package com.ridemove.infrastructure.adapter.out.persistence;

import com.ridemove.domain.model.Rol;
import com.ridemove.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioJpaEntity aEntidad(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getContrasenaCifrada(),
                usuario.getTelefono(),
                usuario.getRol().name(),
                usuario.getFechaRegistro()
        );
    }

    public Usuario aDominio(UsuarioJpaEntity entidad) {
        return new Usuario(
                entidad.getId(),
                entidad.getNombre(),
                entidad.getCorreo(),
                entidad.getContrasena(),
                entidad.getTelefono(),
                Rol.valueOf(entidad.getRol()),
                entidad.getFechaRegistro()
        );
    }
}
