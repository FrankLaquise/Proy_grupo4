package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuariosRegistrado,Integer> {


    UsuariosRegistrado findByCorreo(String email);






}
