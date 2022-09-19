package com.example.proy_grupo4.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
@Getter
@Setter
@Embeddable
public class UsuariosXIncidenciaId implements Serializable {

    @Column(name = "usuario", nullable = false, length = 8)
    private String usuario;

    @Column(name = "idincidencia", nullable = false)
    private Integer idincidencia;



}