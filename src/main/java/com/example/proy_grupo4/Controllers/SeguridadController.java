package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.proy_grupo4.Entity.Incidencia;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seguridad")
public class SeguridadController {
    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    SeguridadRepository seguridadRepository;

    @Autowired
    ComentariosRepository comentariosRepository;

    @Autowired
    ComentariosRepository usuarioRepository;

    @GetMapping(value = {"/login2F"})
    public String Seguridadlogin2F(){
        return "Seguridad_login2F";
    }

    //Para la vista de inicio
    @GetMapping(value = {"/inicio"})
    public String Seguridadincidenciaslistar(Model model){
        List<Incidencia> lista = incidenciaRepository.findAll();
        model.addAttribute("listaIncidencias", lista);
        return "Seguridad_ListaIncidencias2";
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
        return "Seguridad_MapaIncidencias";
    }

    //Para ver el detalle de las incidencias
    @GetMapping(value = {"/detalle"})
    public String SeguridadDetalle(Model model, @RequestParam("id") int id){
        Optional<Incidencia> optInc = incidenciaRepository.findById(id);
        String codigoCreador = incidenciaRepository.buscarCreador(id);

        List<String> comentarios= comentariosRepository.ComentariosporidInc(id);
        if(optInc.isPresent()){
            Incidencia incidencia=optInc.get();
            System.out.println(incidencia);

            model.addAttribute("comentarios",comentarios);
            model.addAttribute("incidencia",incidencia);
            model.addAttribute("codigocreador",codigoCreador);
            return "Seguridad_IncidenciaDetalle";
        }else{
            return "redirect:/seguridad/inicio";
        }

    }

    //Para actualizar el estado de una incidencia(Comentarios+Estado) *Falta implementar validacion en el form
    @PostMapping(value = {"/actualizarest"})
    public String Actualizaresst(Model model, Incidencia incidencia, String comentario, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            incidenciaRepository.Actualizar(id, incidencia.getEstado());
            if(comentario != null) comentariosRepository.IngresarComentxIdinci(comentario,id);

        }
        System.out.println(comentario);
        return  "redirect:/seguridad/inicio";
    }

    //Para aumentar en uno el contador del reporte del usuario(En proceso)
    @PostMapping(value = {"/aumentarreporte"})
    public String Aumentarrep(Model model, Incidencia incidencia, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            incidenciaRepository.Actualizar(id, incidencia.getEstado());
        }
        return  "redirect:/seguridad/inicio";
    }

    //Para acceder al perfil del seguridad
    @GetMapping("/perfil")
    public String perfil(Model model) {
        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = seguridadRepository.findById("20110000");
        if (optionalUsuariosRegistrado.isPresent()) {
            UsuariosRegistrado seguridad= optionalUsuariosRegistrado.get();
            model.addAttribute("seguridad", seguridad);
            return "Seguridad_Perfil";
        } else {
            return "redirect:/seguridad/inicio";
        }
    }

    //Con estas funciones se actualiza el estado de la incidencia
    @GetMapping(value = {"/actualizaratendido"})
    public String SeguridadActualizar1(@RequestParam("id") int id){
        incidenciaRepository.ActualizarAtendido(id);
        return "redirect:/seguridad/inicio";
    }
    @GetMapping(value = {"/actualizarenproceso"})
    public String SeguridadActualizar2(@RequestParam("id") int id){
        incidenciaRepository.ActualizarEnproceso(id);

        return "redirect:/seguridad/inicio";
    }
    @GetMapping(value = {"/actualizarregistrado"})
    public String SeguridadActualizar3(@RequestParam("id") int id){
        incidenciaRepository.ActualizarRegistrado(id);

        return "redirect:/seguridad/inicio";
    }

    //Para cambiar el telefono
    @PostMapping(value = {"/cambiotel"})
    public String Seguridadcambiotel(UsuariosRegistrado seguridad){
        Optional<UsuariosRegistrado> opt = seguridadRepository.findById("20110000");
        if (opt.isPresent()) {
            seguridadRepository.actualizarTelefono(seguridad.getTelefono());
        }
        return "redirect:/seguridad/inicio";
    }



}


