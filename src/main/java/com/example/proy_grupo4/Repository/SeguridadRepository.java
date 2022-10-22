package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.TodosLosUsuario;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SeguridadRepository extends JpaRepository<UsuariosRegistrado, String> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE usuarios_registrados SET telefono = ?1 WHERE codigo = \"20110000\"")
    void actualizarTelefono(String phone);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE usuarios_registrados SET numero_reportes = numero_reportes + 1 WHERE codigo = \"20120000\"")
    void actualizarNReportes(int report_number);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE usuarios_registrados SET estado = 'suspendido' WHERE codigo = \"20120000\"")
    void actualizarSuspendido(String status);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,
            value = "update reportpucp.usuarios_registrados set numero_reportes = numero_reportes + 1 where codigo=\"20110000\";")
    void Aumentarreporte(String phone);

}




