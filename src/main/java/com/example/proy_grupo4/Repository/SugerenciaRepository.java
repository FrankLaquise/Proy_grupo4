package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Comentario;
import com.example.proy_grupo4.Entity.Sugerencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SugerenciaRepository extends JpaRepository<Sugerencia, Integer> {

}
