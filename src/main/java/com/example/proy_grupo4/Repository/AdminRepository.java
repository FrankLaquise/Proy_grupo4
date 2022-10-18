package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdminRepository extends JpaRepository<UsuariosRegistrado, String> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE usuarios_registrados SET estado = 'suspendido' WHERE codigo = \"20120000\"")
    void actualizarSuspendido(String status);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO `comentarios` VALUES ('incidencia resuelta!',1?);")
    void actualizarActivo(String status);

}
