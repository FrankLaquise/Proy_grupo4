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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/incidencia")
public class IncidenciaController {
    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Autowired
    ZonaRepository zonaRepository;

    @Autowired
    TipoRepository tipoRepository;
    @GetMapping(value = {"/perfil"})
    public String Perfil(){
        return "perfil_usuario";
    }



    @GetMapping(value = {"/mapa"})
    public String IncidenciaMapa(){
        return "mapa_incidencias";
    }
    @GetMapping(value = {"/destacadas"})
    public String IncidenciaDestacada(){
        return "incidencias_destacadas";
    }
    @GetMapping(value = {"/sugerencias"})
    public String IncidenciaSugerencias(){
        return "sugerencias";
    }


    @GetMapping(value = {"/info"})
    public String IncidenciaInfo(@RequestParam("id_incidencia") String id ,Model model) {
        int id_int=Integer.parseInt(id);
        Optional<Incidencia> opt_incid =incidenciaRepository.findById(id_int);
        if (opt_incid.isPresent()) {
            Incidencia incidencia = opt_incid.get();
            model.addAttribute("incidencia", incidencia);
            return "info_incidencia";
        } else {
            return "redirect:/incidencia/list";
        }

    }



    @GetMapping(value = {"/list"})
    public String misIncidencias(Model model) {

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








}
