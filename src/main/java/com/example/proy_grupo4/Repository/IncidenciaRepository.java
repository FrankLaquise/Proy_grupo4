package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT u.usuario FROM reportpucp.`usuarios_registran/destacan_incidencias` u\n" +
                    "join incidencias i where u.incidencia = i.idincidencias and u.creador = 1 and i.idincidencias=?1")
    String buscarCreador(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE incidencias SET estado = 'atendido' WHERE idincidencias = ?1")
    void ActualizarAtendido(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE incidencias SET estado = 'registrado' WHERE idincidencias = ?1")
    void ActualizarRegistrado(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE incidencias SET estado = 'en proceso' WHERE idincidencias = ?1")
    void ActualizarEnproceso(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE incidencias SET estado = ?2 WHERE idincidencias = ?1")
    void Actualizar(int id,String estado);

    //Falta query para actualizar comentarios(No hay tabla comentarios en SQL)

    @Query(nativeQuery = true,
            value = "SELECT * FROM incidencias where idincidencias=?1")
    Optional<Incidencia> buscarxid(int id);

}


