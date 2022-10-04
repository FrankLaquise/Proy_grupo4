package com.example.proy_grupo4.Entity;

import com.example.proy_grupo4.Entity.Incidencia;

import javax.persistence.*;

@Entity
@Table(name = "`usuarios_registran/destacan_incidencias`")
public class UsuariosXIncidencia {
    @EmbeddedId
    private UsuariosXIncidenciaId id;

    @MapsId("usuario")
    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private UsuariosRegistrado usuario;

    @MapsId("idincidencia")
    @ManyToOne
    @JoinColumn(name = "idincidencia", nullable = false)
    private Incidencia idincidencia;



}