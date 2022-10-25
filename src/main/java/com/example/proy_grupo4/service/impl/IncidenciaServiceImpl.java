package com.example.proy_grupo4.service.impl;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.service.api.IncidenciaServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IncidenciaServiceImpl implements IncidenciaServiceAPI {
    @Autowired
    private IncidenciaRepository incidenciaRepository;

    @Override
    public Page<Incidencia> getAll(Pageable pageable) {
        return incidenciaRepository.findAll(pageable);
    }
    public Page<Incidencia> Filtra(Pageable pageable) {
        return incidenciaRepository.findAll(pageable);

    }

}
