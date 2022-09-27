package com.example.proy_grupo4.Entity;

import javax.persistence.*;

@Entity
@Table(name = "usuarios_registrados")
public class UsuariosRegistrado {
    @Id
    @Column(name = "codigo", nullable = false, length = 8)
    private String id;

    @Column(name = "nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 45)
    private String apellido;

    @Column(name = "dni", nullable = false, length = 45)
    private String dni;

    @Column(name = "correo", nullable = false, length = 45)
    private String correo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rol", nullable = false)
    private Roles rol;

    @Column(name = "telefono", nullable = false, length = 45)
    private String telefono;

    @Column(name = "contrasena", length = 100)
    private String contrasena;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "estado", nullable = false, length = 45)
    private String estado;

    @Column(name = "numero_reportes", nullable = false)
    private Integer numeroReportes;

    @Column(name = "comentario_suspension", nullable = true, length = 80)
    private String comentarioSuspension;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "icono", nullable = false)
    private Icono icono;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getNumeroReportes() {
        return numeroReportes;
    }

    public void setNumeroReportes(Integer numeroReportes) {
        this.numeroReportes = numeroReportes;
    }

    public String getComentarioSuspension() {
        return comentarioSuspension;
    }

    public void setComentarioSuspension(String comentarioSuspension) {
        this.comentarioSuspension = comentarioSuspension;
    }

    public Icono getIcono() {
        return icono;
    }

    public void setIcono(Icono icono) {
        this.icono = icono;
    }

}