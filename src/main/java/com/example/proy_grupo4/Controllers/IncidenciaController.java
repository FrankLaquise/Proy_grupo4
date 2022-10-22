package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.Comentario;
import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/incidencia")
public class IncidenciaController {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ZonaRepository zonaRepository;

    @Autowired
    TipoRepository tipoRepository;

    @Autowired
    ComentariosRepository comentariosRepository;



    @GetMapping("/perfil")
    public String perfil(Model model) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = usuarioRepository.findById("20120000");

        if (optionalUsuariosRegistrado.isPresent()) {
            UsuariosRegistrado usuario= optionalUsuariosRegistrado.get();
            model.addAttribute("usuario", usuario);
            return "Usuario_Perfil";
        } else {
            return "redirect:/incidencia";
        }
    }


    @GetMapping(value = {"/mapa"})
    public String IncidenciaMapa(){
        return "Usuario_MapaIncidencias";
    }
    @GetMapping(value = {"/destacadas"})
    public String IncidenciaDestacada(Model model){
        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);
        return "Usuario_IncidenciasDestacadas";
    }
    @GetMapping(value = {"/sugerencias"})
    public String IncidenciaSugerencias(){
        return "Usuario_Sugerencias";
    }
    @GetMapping(value = {"/mis_incidencias"})
    public String MisIncidencias(Model model){
        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);
        return "Usuario_MisIncidencias";
    }


    @GetMapping(value = {"/info"})
    public String IncidenciaInfo(@RequestParam("idincidencias") int idincidencias ,Model model) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        Incidencia incidencia = optionalIncidencia.get();
        incidencia.setDestacado(1);
        incidenciaRepository.save(incidencia);
        return "redirect:/incidencia/list";
    }

    @GetMapping(value = {"/destacar"})
    public String Incidenciadestacar(@RequestParam("idincidencias") int idincidencias) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        Incidencia Incidencia = optionalIncidencia.get();
        Incidencia.setDestacado(1);
        incidenciaRepository.save(Incidencia);
        return "redirect:/incidencia/list";
    }

    @GetMapping(value = {"/desdestacar"})
    public String Incidenciadesdestacar(@RequestParam("idincidencias") int idincidencias) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        Incidencia Incidencia = optionalIncidencia.get();
        Incidencia.setDestacado(0);
        incidenciaRepository.save(Incidencia);
        return "redirect:/incidencia/list";
    }

    @GetMapping("/comentar")
    public String comentar(Comentario comentario) {
        comentariosRepository.save(comentario);
        return "redirect:/incidencia/list";
    }
    @GetMapping(value = {"/list",""})
    public String Incidencias(Model model) {

        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);

        return "Usuario_ListaIncidencias";
    }

    @GetMapping("/new")
    public String nuevoTransportistaFrm(Model model , Incidencia incidencia) {
        model.addAttribute("listaZonas",zonaRepository.findAll());
        model.addAttribute("listaTipos",tipoRepository.findAll());
        return "Usuario_RegistroIncidencia";
    }



    @PostMapping("/save")
    public String guardarProducto(Incidencia incidencia) {
        incidencia.setEstado("registrado");
        incidencia.setNumeroReportes(1);
        incidencia.setHoraCreacion(Instant.now());
        incidencia.setDestacado(0);
        incidenciaRepository.save(incidencia);
        return "redirect:/incidencia";
    }

    @PostMapping(value = {"/cambiotel"})
    public String usuariocambiotel(UsuariosRegistrado usuario){
        Optional<UsuariosRegistrado> opt = usuarioRepository.findById("20120000");
        if (opt.isPresent()) {
            usuarioRepository.actualizarTelefono(usuario.getTelefono());
        }
        return "redirect:/incidencia";
    }




}
