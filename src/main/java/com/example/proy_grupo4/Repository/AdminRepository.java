package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<UsuariosRegistrado, Integer> {
}
