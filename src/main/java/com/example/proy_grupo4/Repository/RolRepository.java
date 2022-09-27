package com.example.proy_grupo4.Repository;

import com.example.proy_grupo4.Entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Roles, Integer> {
}
