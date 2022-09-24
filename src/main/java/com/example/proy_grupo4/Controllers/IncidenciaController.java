package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.Tipo;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.Repository.TipoRepository;
import com.example.proy_grupo4.Repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/incidencia")
public class IncidenciaController {
    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Autowired
    ZonaRepository zonaRepository;

    @Autowired
    TipoRepository tipoRepository;
    @GetMapping(value = {"/list"})
    public String listarIncidencias(Model model) {

        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);

        return "lista_incidencias";

    }

    @GetMapping("/new")
    public String nuevoTransportistaFrm(Model model , Incidencia incidencia) {
        model.addAttribute("listaZonas",zonaRepository.findAll());

        model.addAttribute("listaTipos",tipoRepository.findAll());
        return "Registro_incidencias";
    }
    @PostMapping("/save")
    public String guardarProducto(Incidencia incidencia, RedirectAttributes attr) {
        incidenciaRepository.save(incidencia);
        return "redirect:/incidencia/list";
    }







}
