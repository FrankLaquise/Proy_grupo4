package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.Comentario;
import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.service.api.IncidenciaServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        model.addAttribute("Incidencia", incidencia);
        return "Usuario_InfoIncidencia";
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


    @GetMapping(value = {"/recuperar_contra"})
    public String RecuperarContra(){
        return "Login_RecuperarContra";
    }

    //paginacion_INICIO...listanormal
    @Autowired
    private IncidenciaServiceAPI incidenciaServiceAPI;
    @GetMapping(value = {"/list",""})
    public String findAll(@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
        Page<Incidencia> pageIncidencia = incidenciaServiceAPI.getAll(pageRequest);

        int totalPage  = pageIncidencia.getTotalPages();
        if (totalPage>0){
            List<Integer> pages  = IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
            model.addAttribute("pages",pages);
        }
        model.addAttribute("listaIncidencias",pageIncidencia.getContent());
        model.addAttribute("current",page+1);
        model.addAttribute("next",page+2);
        model.addAttribute("prev",page);
        model.addAttribute("last",totalPage);
        return "Usuario_ListaIncidencias";
    }
    //paginacion_FINAL...listanorma

    @PostMapping("/BuscarxZona")
    public String BuscarxZona(@RequestParam("searchField") String searchField,Model model){
        List<Incidencia> listaIncidencias = incidenciaRepository.busquedaParcialTitulo(searchField);
        model.addAttribute("listaIncidencias",listaIncidencias);
        model.addAttribute("searchField",searchField);
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
    public String usuariocambiotel(UsuariosRegistrado usuario, @RequestParam("id") String id){
        Optional<UsuariosRegistrado> opt = usuarioRepository.findById(id);
        if (opt.isPresent()) {
            usuarioRepository.actualizarTelefono(usuario.getTelefono(),id);
        }
        return "redirect:/incidencia";
    }




}
