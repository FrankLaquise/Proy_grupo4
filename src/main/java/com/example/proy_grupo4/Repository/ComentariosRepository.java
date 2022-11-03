package com.example.proy_grupo4.Repository;


import com.example.proy_grupo4.Entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentario, Integer> {
    /*
    @Query(nativeQuery = true,
            value = "SELECT u.texto FROM reportpucp.`comentarios` u where u.incidencia = ?1")
    List<Comentario> ComentariosporidInc(int id);
    */
    @Query(nativeQuery = true,
            value = "SELECT texto FROM reportpucp.comentarios where incidencia = ?1")
    List<String> ComentariosporidInc(int id);

    @Query(nativeQuery = true,
            value = "SELECT u.idcomentarios FROM reportpucp.`comentarios` u where u.incidencia = ?1")
    int idComenarioporidInc(int id);

    @Query(nativeQuery = true,
            value = "SELECT * FROM comentarios where incidencia = ?1")
    List<Comentario> ComentariosporidInci(int id);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "insert into reportpucp.comentarios (texto,incidencia,tipo,fecha)\n" +
                    "values (?1, ?2,?3,?4)")
    void IngresarComentxIdinci(String comment, int id, String tipo, Instant fecha);

}
