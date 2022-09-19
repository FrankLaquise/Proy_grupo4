package com.example.proy_grupo4.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "iconos")
public class Icono {
    @Id
    @Column(name = "idiconos", nullable = false)
    private Integer id;

    @Column(name = "imagen", nullable = false)
    private byte[] imagen;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

}