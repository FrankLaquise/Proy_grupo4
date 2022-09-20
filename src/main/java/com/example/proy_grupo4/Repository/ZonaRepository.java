package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona,Integer> {

}
