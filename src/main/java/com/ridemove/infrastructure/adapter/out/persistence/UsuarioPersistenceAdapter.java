package com.ridemove.infrastructure.adapter.out.persistence;

import com.ridemove.domain.model.Usuario;
import com.ridemove.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adaptador de salida: traduce el puerto de dominio a operaciones concretas de JPA/PostgreSQL.
 * Si mañana cambia la base de datos, solo se reemplaza este adaptador.
 */
@Component
public class UsuarioPersistenceAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper mapper;

    public UsuarioPersistenceAdapter(UsuarioJpaRepository jpaRepository, UsuarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioJpaEntity guardado = jpaRepository.save(mapper.aEntidad(usuario));
        return mapper.aDominio(guardado);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return jpaRepository.findByCorreo(correo).map(mapper::aDominio);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return jpaRepository.existsByCorreo(correo);
    }
}
