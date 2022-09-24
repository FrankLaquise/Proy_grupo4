package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipo,Integer> {

}
