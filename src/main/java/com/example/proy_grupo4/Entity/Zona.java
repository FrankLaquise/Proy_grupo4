package com.example.proy_grupo4.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "zonas")
public class Zona {
    @Id
    @Column(name = "idzonas", nullable = false)
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 60)
    private String titulo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}