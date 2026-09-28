package com.ridemove.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "correo", length = 60, nullable = false, unique = true)
    private String correo;

    @Column(name = "contrasena", length = 100, nullable = false)
    private String contrasena;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "rol", length = 20, nullable = false)
    private String rol;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    protected UsuarioJpaEntity() {
        // requerido por JPA
    }

    public UsuarioJpaEntity(Long id, String nombre, String correo, String contrasena,
                             String telefono, String rol, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getContrasena() { return contrasena; }
    public String getTelefono() { return telefono; }
    public String getRol() { return rol; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
}
