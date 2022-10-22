package com.example.proy_grupo4.service.api;

import com.example.proy_grupo4.Entity.Incidencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IncidenciaServiceAPI {
    Page<Incidencia> getAll(Pageable pageable);

}
