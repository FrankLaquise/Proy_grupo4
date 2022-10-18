package com.example.proy_grupo4.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "incidencias")
public class Incidencia {
    @Id
    @Column(name = "idincidencias", nullable = false)
    private Integer id;

    @Column(name = "hora_creacion", nullable = false)
    private Instant horaCreacion;

    @Positive
    @Column(name = "numero_reportes", nullable = false)
    private Integer numeroReportes;

    @Column(name = "estado", nullable = false, length = 45)
    private String estado;

    @Column(name = "foto", nullable = false)
    private byte[] foto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "icono", nullable = false)
    private Icono icono;

    @NotNull
    @Column(name = "titulo", nullable = false, length = 45)
    private String titulo;

    @NotNull
    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;

    @Column(name = "calificacion", precision = 3, scale = 2)
    private BigDecimal calificacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo", nullable = false)
    private Tipo tipo;

    @Column(name = "nivel", nullable = false, length = 45)
    private String nivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona", nullable = false)
    private Zona zona;

    @NotNull
    @Column(name = "zona_detalles", length = 80)
    private String zonaDetalles;

    @Column(name = "latitud", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(name = "longitud", nullable = false, precision = 10, scale = 8)
    private BigDecimal longitud;

    @Column(name = "comentarios_restantes", nullable = false)
    private Integer comentariosRestantes;

    @Column(name = "res", nullable = false)
    private Byte res;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(Instant horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Integer getNumeroReportes() {
        return numeroReportes;
    }

    public void setNumeroReportes(Integer numeroReportes) {
        this.numeroReportes = numeroReportes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public Icono getIcono() {
        return icono;
    }

    public void setIcono(Icono icono) {
        this.icono = icono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    public String getZonaDetalles() {
        return zonaDetalles;
    }

    public void setZonaDetalles(String zonaDetalles) {
        this.zonaDetalles = zonaDetalles;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    public Integer getComentariosRestantes() {
        return comentariosRestantes;
    }

    public void setComentariosRestantes(Integer comentariosRestantes) {
        this.comentariosRestantes = comentariosRestantes;
    }

    public Byte getRes() {
        return res;
    }

    public void setRes(Byte res) {
        this.res = res;
    }

}