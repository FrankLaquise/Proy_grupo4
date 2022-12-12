package com.example.proy_grupo4.service.impl;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class NewUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    public Page<UsuariosRegistrado> findProductsWithPaginationAndSorting(int offset, int pageSize, String field){
        Page<UsuariosRegistrado> products = usuarioRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field).descending()));
        return  products;
    }

    public Page<UsuariosRegistrado> findProductsWithPaginationAndSorting_destac(int offset, int pageSize, String field1,String field2){
        Page<UsuariosRegistrado> products = usuarioRepository.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field1).descending().and(Sort.by(field2).descending())));
        return  products;
    }
}
