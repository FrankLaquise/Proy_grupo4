package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<UsuariosRegistrado, String> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM reportpucp.usuarios_registrados i join roles r on i.rol=r.idroles where i.codigo like %?1% ;")
    List<UsuariosRegistrado> busquedaParcialCodigo(String titulo);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE usuarios_registrados SET foto =?2 WHERE codigo =?1")
    void actualizar(String id, byte[] foto);


    @Query(nativeQuery = true,
            value = "SELECT * FROM usuarios_registrados i where i.correo=?1")
    UsuariosRegistrado buscarxcorreo(String correo);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO `comentarios` VALUES ('incidencia resuelta!',1?);")
    void actualizarActivo(String status);

}
