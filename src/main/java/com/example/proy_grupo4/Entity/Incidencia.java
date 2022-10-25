package com.example.proy_grupo4.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "incidencias")
public class Incidencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idincidencias")
    private Integer id;


    @Column(name = "hora_creacion")
    private Instant horaCreacion;


    @Column(name = "numero_reportes")
    private Integer numeroReportes;

    @Size(max = 45)

    @Column(name = "estado", length = 45)
    private String estado;


    @Column(name = "foto" )
    private byte[] foto;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "icono")
    private Icono icono;

    @Size(max = 45)

    @Column(name = "titulo", length = 45)
    private String titulo;

    @Size(max = 150)

    @Column(name = "descripcion",  length = 150)
    private String descripcion;

    @Column(name = "calificacion", precision = 3, scale = 2)
    private BigDecimal calificacion;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tipo")
    private Tipo tipo;

    @Size(max = 45)

    @Column(name = "nivel", length = 45)
    private String nivel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zona")
    private Zona zona;

    @Size(max = 80)
    @Column(name = "zona_detalles", length = 80)
    private String zonaDetalles;


    @Column(name = "latitud", precision = 10, scale = 8)
    private BigDecimal latitud;


    @Column(name = "longitud",  precision = 10, scale = 8)
    private BigDecimal longitud;


    @Column(name = "comentarios_restantes")
    private Integer comentariosRestantes;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "destacado")
    private Integer destacado;
    @Column(name = "res")
    private Byte res;

    @Column(name = "comentariorep", length = 45)
    private String comentariorep;

    public String getComentariorep() {
        return comentariorep;
    }

    public void setComentariorep(String comentariorep) {
        this.comentariorep = comentariorep;
    }

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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getDestacado() {
        return destacado;
    }

    public void setDestacado(Integer destacado) {
        this.destacado = destacado;
    }
}