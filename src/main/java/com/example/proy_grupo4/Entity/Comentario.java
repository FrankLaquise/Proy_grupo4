package com.example.proy_grupo4.Entity;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
public class Comentario {
    @Id
    @Column(name = "idcomentarios", nullable = false)
    private Integer id;

    @Column(name = "texto", nullable = false, length = 80)
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "incidencia", nullable = false)
    private Incidencia incidencia;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }

}