package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.TodosLosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodosLosUsuario, Integer> {
}
