package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT u.usuario FROM `usuarios_registran/destacan_incidencias` u \n" +
                    "join incidencias i where u.incidencia = i.idincidencias and creador = 1 and idincidencias = %?1%")
    String buscarCreador(int id);

}


