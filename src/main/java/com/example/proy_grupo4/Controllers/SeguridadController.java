package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @GetMapping(value = {"/login2F"})
    public String Seguridadlogin2F(){
        return "Seguridad_login2F";
    }

    @PostMapping(value = {"/inicio"})
    public String SeguridadInicio(Model model){
        List<Incidencia> incidencias = incidenciaRepository.findAll();
        return "Seguridad_inicio";
    }


    @GetMapping(value = {"/incidencias"})
    public String Seguridadincidencias(){
        return "Seguridad_inicio";
    }

    @GetMapping(value = {"/incidenciaslistar"})
    public String Seguridadincidenciaslistar(Model model){
        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);
        return "Seguridad_inicio2";
    }

    @GetMapping(value = {"/exportar"})
    public String SeguridadExportar(){
        return "Seguridad_exportar";
    }
    @GetMapping(value = {"/dashboard"})
    public String SeguridadDashboard(){
        return "Seguridad_Dashboard";
    }
    @GetMapping(value = {"/mapa"})
    public String SeguridadMapa(){
        return "Seguridad_mapa";
    }
}


