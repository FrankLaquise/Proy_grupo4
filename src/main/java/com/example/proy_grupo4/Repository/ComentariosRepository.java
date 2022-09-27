package com.example.proy_grupo4.Repository;


import com.example.proy_grupo4.Entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentariosRepository extends JpaRepository<Comentario, Integer> {
    /*
    @Query(nativeQuery = true,
            value = "SELECT u.texto FROM reportpucp.`comentarios` u where u.incidencia = ?1")
    List<Comentario> ComentariosporidInc(int id);
    */
    @Query(nativeQuery = true,
            value = "SELECT u.texto FROM reportpucp.`comentarios` u where u.incidencia = ?1")
    List<String> ComentariosporidInc(int id);

    @Query(nativeQuery = true,
            value = "SELECT u.idcomentarios FROM reportpucp.`comentarios` u where u.incidencia = ?1")
    int idComenarioporidInc(int id);

}
