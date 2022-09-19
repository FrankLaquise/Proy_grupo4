package com.example.proy_grupo4.Entity;

import javax.persistence.*;

@Entity
@Table(name = "sugerencias")
public class Sugerencia {
    @Id
    @Column(name = "idsugerencias", nullable = false)
    private Integer id;

    @Column(name = "texto", nullable = false, length = 150)
    private String texto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario", nullable = false)
    private UsuariosRegistrado usuario;

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

    public UsuariosRegistrado getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuariosRegistrado usuario) {
        this.usuario = usuario;
    }

}