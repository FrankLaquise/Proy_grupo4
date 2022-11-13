package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Entity.Comentario;
import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.Sugerencia;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.service.api.IncidenciaServiceAPI;
import com.example.proy_grupo4.service.impl.NewIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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
    SugerenciaRepository sugerenciaRepository;
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
    public String IncidenciaDestacada(@RequestParam(name="buscarx" , required = false) String buscarx,@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
        //Page<Incidencia> pageIncidencia = incidenciaServiceAPI.getAll(pageRequest);
        if (buscarx != null){
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting_destac(page,6,buscarx,"destacado");
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

            model.addAttribute("ordenarpor",buscarx);
        }else {
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting_destac(page,6,"titulo","destacado");
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

            model.addAttribute("ordenarpor","horaCreacion");
        }


        return "Usuario_IncidenciasDestacadas";
    }


    @GetMapping(value = {"/sugerencias"})
    public String IncidenciaSugerencias(){
        return "Usuario_Sugerencias";
    }

    @GetMapping(value = {"/mis_incidencias"})
    public String MisIncidencias(@RequestParam(name="buscarx" , required = false) String buscarx,@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
        //Page<Incidencia> pageIncidencia = incidenciaServiceAPI.getAll(pageRequest);
        if (buscarx != null){
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,6,buscarx);
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

            model.addAttribute("ordenarpor",buscarx);
        }else {
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,6,"titulo");
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

            model.addAttribute("ordenarpor","horaCreacion");
        }


        return "Usuario_MisIncidencias";
    }


    @GetMapping(value = {"/info"})
    public String IncidenciaInfo(@RequestParam("idincidencias") int idincidencias ,Model model) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        List<Comentario> comentarios = comentariosRepository.ComentariosporidInci(idincidencias);
        if (optionalIncidencia.isPresent()) {
            Incidencia incidencia = optionalIncidencia.get();
            model.addAttribute("comentarios", comentarios);
            model.addAttribute("Incidencia", incidencia);
            return "Usuario_InfoIncidencia";
        }else{
            return "redirect:/incidencia/list";
        }

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
        return "redirect:/incidencia/list/";
    }

    @PostMapping(value = {"/comentar"})
    public String comentarincidencia(Model model, Incidencia incidencia, String comentario, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            if(comentario!=null) {
                if(comentario.length() != 0) {
                    System.out.println("El comentario es:" + comentario);
                    //Dependiendo del 3er parametro ingresa comentario seguridad/usuario
                    comentariosRepository.IngresarComentxIdinci(comentario, id, "usuario", Instant.now());
                }
            }
        }
        System.out.println(comentario);
        return  "redirect:/incidencia/info?idincidencias="+id;
    }


    @GetMapping(value = {"/recuperar_contra"})
    public String RecuperarContra(){
        return "Login_RecuperarContra";
    }

    //paginacion_INICIO...listanormal
    @Autowired
    private IncidenciaServiceAPI incidenciaServiceAPI;

    @Autowired
    private NewIncidenciaService newIncidenciaService;
    @GetMapping(value = {"/list",""})
    public String findAll(@RequestParam(name="buscarx" , required = false) String buscarx,@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
        //Page<Incidencia> pageIncidencia = incidenciaServiceAPI.getAll(pageRequest);
if (buscarx != null){
    Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,6,buscarx);
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

    model.addAttribute("ordenarpor",buscarx);
}else {
    Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,6,"titulo");
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

    model.addAttribute("ordenarpor","horaCreacion");
}


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
    public String guardarProducto(  Incidencia incidencia) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        incidencia.setRes((byte) 1);
        incidencia.setEstado("registrado");
        incidencia.setNumeroReportes(1);
        incidencia.setHoraCreacion(Instant.now());
        incidencia.setDestacado(0);
        incidencia.setComentariosRestantes(100);
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


    @PostMapping(value = {"/regsugerencias"})
    public String usuarioregsugerencia(Sugerencia sugerencia) {
        //sugerencia.setId();
        sugerenciaRepository.save(sugerencia);
        return "redirect:/incidencia/";

    }
}
