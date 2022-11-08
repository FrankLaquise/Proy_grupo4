package com.example.proy_grupo4.service.impl;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewIncidenciaService {
    @Autowired
    private IncidenciaRepository incidenciaRepository;
    public Page<Incidencia> findProductsWithPaginationAndSorting(int offset, int pageSize, String field){
        Page<Incidencia> products = incidenciaRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field).descending()));
        return  products;
    }

    public Page<Incidencia> findProductsWithPaginationAndSorting_destac(int offset, int pageSize, String field1,String field2){
        Page<Incidencia> products = incidenciaRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field1).descending().and(Sort.by(field2).descending())));
        return  products;
    }
}
