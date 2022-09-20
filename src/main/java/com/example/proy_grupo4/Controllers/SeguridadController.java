package com.example.proy_grupo4.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seguridad")
public class SeguridadController {

    @GetMapping(value = {"/login2F"})
    public String Seguridadlogin2F(){
        return "Seguridad_login2F";
    }

    @PostMapping(value = {"/inicio"})
    public String SeguridadInicio(){
        return "Seguridad_inicio";
    }

    @GetMapping(value = {"/incidencias"})
    public String Seguridadincidencias(){
        return "Seguridad_inicio";
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


