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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @GetMapping(value = {"/login2F"})
    public String Seguridadlogin2F(){
        return "Seguridad_login2F";
    }

    @GetMapping(value = {"/inicio"})
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

    @GetMapping(value = {"/detalle"})
    public String SeguridadDetalle(Model model, @RequestParam("id") int id){
        Optional<Incidencia> optInc = incidenciaRepository.findById(id);
        if(optInc.isPresent()){
            Incidencia incidencia=optInc.get();
            model.addAttribute("incidencia",incidencia);
            return "Seguridad_IncidenciaDetalle";
        }else{
            return "redirect:/seguridad/inicio";
        }

    }

    @GetMapping(value = {"/perfil"})
    public String SeguridadPerfil(){
        return "Seguridad_perfil";
    }




}


