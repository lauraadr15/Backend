package com.ridemove.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidad de dominio Usuario.
 * No depende de JPA, Spring ni ningún framework: es el núcleo del negocio (Arquitectura Hexagonal).
 */
public class Usuario {

    private final Long id;
    private final String nombre;
    private final String correo;
    private final String contrasenaCifrada;
    private final String telefono;
    private final Rol rol;
    private final LocalDateTime fechaRegistro;

    public Usuario(Long id, String nombre, String correo, String contrasenaCifrada,
                    String telefono, Rol rol, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = Objects.requireNonNull(nombre, "El nombre es obligatorio");
        this.correo = Objects.requireNonNull(correo, "El correo es obligatorio");
        this.contrasenaCifrada = Objects.requireNonNull(contrasenaCifrada, "La contraseña es obligatoria");
        this.telefono = telefono;
        this.rol = Objects.requireNonNull(rol, "El rol es obligatorio");
        this.fechaRegistro = fechaRegistro;
    }

    /** Fábrica para crear un usuario nuevo (aún sin id, lo asigna la persistencia). */
    public static Usuario nuevo(String nombre, String correo, String contrasenaCifrada,
                                 String telefono, Rol rol) {
        return new Usuario(null, nombre, correo, contrasenaCifrada, telefono, rol, LocalDateTime.now());
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasenaCifrada() { return contrasenaCifrada; }
    public String getTelefono() { return telefono; }
    public Rol getRol() { return rol; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}
