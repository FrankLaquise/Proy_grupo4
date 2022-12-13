package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Email;
import com.example.proy_grupo4.Entity.*;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.service.impl.NewIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.time.Instant;
import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
    private final List<String> formatos= Arrays.asList("media/png","media/jpeg", "image/jpeg", "image/png");
    @Autowired
    private Email sender;
    public void sendMail(String destino, String subjet, String body){
        sender.sendEmail(destino,subjet,body);
    }
    @GetMapping("/perfil")
    public String perfil(Model model , HttpSession session) {
        UsuariosRegistrado user = (UsuariosRegistrado) session.getAttribute("usuario");
        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = usuarioRepository.findById(user.getId());

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
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting_destac(page,4,buscarx,"destacado");
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
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting_destac(page,4,"titulo","destacado");
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
        if (buscarx != null){
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,4,buscarx);
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
            Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,4,"titulo");
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
            return "Error_404";
        }

    }

    @GetMapping(value = {"/destacar"})
    public String Incidenciadestacar(@RequestParam("idincidencias") int idincidencias) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        Incidencia Incidencia = optionalIncidencia.get();
        Incidencia.setDestacado(1);
        incidenciaRepository.save(Incidencia);
        return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";
    }

    @GetMapping(value = {"/desdestacar"})
    public String Incidenciadesdestacar(@RequestParam("idincidencias") int idincidencias) {
        Optional<Incidencia> optionalIncidencia = incidenciaRepository.buscarxid(idincidencias);
        Incidencia Incidencia = optionalIncidencia.get();
        Incidencia.setDestacado(0);
        incidenciaRepository.save(Incidencia);
        return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";
    }

    @PostMapping(value = {"/comentar"})
    public String comentarincidencia(String comentario, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        Incidencia incidencia = opt.get();
        incidencia.setEstado("en proceso");
        incidenciaRepository.save(incidencia);
        if(opt.isPresent()){
            if(comentario!=null) {
                if(comentario.length() != 0) {
                    System.out.println("El comentario es:" + comentario);
                    //Dependiendo del 3er parametro ingresa comentario seguridad/usuario
                    comentariosRepository.IngresarComentxIdinci(comentario, id, "usuario", Instant.now());
                }
            }
        }else{
            return "Error_404";
        }
        return  "redirect:/incidencia/info?idincidencias="+id;
    }

    @PostMapping(value = {"/calificar"})
    public String calificar(int calificacion, @RequestParam("id") int id){
        incidenciaRepository.calificacificar(id,calificacion);
        return  "redirect:/incidencia/info?idincidencias="+id;
    }
    @GetMapping(value = {"/recuperar_contra"})
    public String RecuperarContra(){
        return "Login_RecuperarContra";
    }

    @Autowired
    private NewIncidenciaService newIncidenciaService;
    @GetMapping(value = {"/list",""})
    public String findAll(@RequestParam(name="buscarx" , required = false) String buscarx,@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
        //Page<Incidencia> pageIncidencia = incidenciaServiceAPI.getAll(pageRequest);
if (buscarx != null){
    Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,4,buscarx);
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
    Page<Incidencia> pageIncidencia = newIncidenciaService.findProductsWithPaginationAndSorting(page,4,"titulo");
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
    @PostMapping("/BuscarxZona")
    public String BuscarxZona(@RequestParam("searchField") String searchField,Model model){
        List<Incidencia> listaIncidencias = incidenciaRepository.busquedaParcialTitulo(searchField);
        model.addAttribute("listaIncidencias",listaIncidencias);
        model.addAttribute("searchField",searchField);
        return "Usuario_ListaIncidencias";
    }
    @GetMapping("/new")
    public String nuevoTransportistaFrm(Model model) {
        model.addAttribute("listaZonas",zonaRepository.findAll());
        model.addAttribute("listaTipos",tipoRepository.findAll());
        return "Usuario_RegistroIncidencia";
    }
    @PostMapping("/save")
    public String guardar(Incidencia incidencia, @RequestParam("file") MultipartFile imagen) throws IOException {
        if(!imagen.isEmpty()){
            try {
                byte[] bytesImg = imagen.getBytes();
                incidencia.setFoto(bytesImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        incidencia.setRes((byte) 1);
        incidencia.setEstado("registrado");
        incidencia.setNumeroReportes(1);
        incidencia.setHoraCreacion(Instant.now());
        incidencia.setDestacado(0);
        incidencia.setComentariosRestantes(100);
        incidencia.setCalificacion(0);
        incidenciaRepository.save(incidencia);
        return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";
    }

    @PostMapping(value = {"/cambio"})
    public String usuariocambiotel(UsuariosRegistrado usuario, @RequestParam("id") String id, @RequestParam("file") MultipartFile imagen){
        Optional<UsuariosRegistrado> opt = usuarioRepository.findById(id);
        UsuariosRegistrado usuariosRegistrado = opt.get();
        if(!imagen.isEmpty()){
            try {
                byte[] bytesImg = imagen.getBytes();
                usuariosRegistrado.setFoto(bytesImg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        usuariosRegistrado.setTelefono(usuario.getTelefono());
        usuarioRepository.actualizar(usuariosRegistrado.getTelefono(),id);
        return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";
    }

    @RequestMapping(value ={"/resuelto"})
    public String resuelto(@RequestParam("id") int id){
        Optional<Incidencia> incidencia1 = incidenciaRepository.findById(id);
        Incidencia incidencia = incidencia1.get();
        incidencia.setEstado("resuelto");
        Comentario comentario = new Comentario();
        comentario.setIncidencia(incidencia);
        comentario.setTipo("usuario");
        comentario.setTexto("Satisfecho");
        comentario.setFecha(Instant.now());
        comentariosRepository.save(comentario);
        incidenciaRepository.save(incidencia);
        return "redirect:/incidencia/info?idincidencias="+id;
    }

    @RequestMapping(value ={"/eliminar"})
    public String elimina(@RequestParam("id") int id){
        Optional<Incidencia> incidencia1 = incidenciaRepository.findById(id);
        Incidencia incidencia = incidencia1.get();
        incidenciaRepository.delete(incidencia);
        return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";
    }


    @PostMapping(value = {"/regsugerencias"})
    public String usuarioregsugerencia(Sugerencia sugerencia) {
        sender.sendEmail("a20190212@pucp.edu.pe","Nueva Sugerencia","Se ha registardo nueva sugerencia:\n"
                + sugerencia.getTexto());
        sugerenciaRepository.save(sugerencia);
        return "redirect:/incidencia/sugerencias";

    }

    @GetMapping(value={"/display"})
    @ResponseBody
    void mostrar(@RequestParam("id") String id, HttpServletResponse response, Optional<UsuariosRegistrado> usuariosRegistrado)
            throws ServletException, IOException {
        usuariosRegistrado = usuarioRepository.findById(id);
        response.setContentType("image/jpeg,image/jpg,image/png");
        response.getOutputStream().write(usuariosRegistrado.get().getFoto());
        response.getOutputStream().close();
    }

    @GetMapping(value={"/display2"})
    @ResponseBody
    void mostrar1(@RequestParam("id") int id, HttpServletResponse response, Optional<Incidencia> incidencia)
            throws ServletException, IOException {
        incidencia = incidenciaRepository.findById(id);
        response.setContentType("image/jpeg,image/jpg,image/png");
        response.getOutputStream().write(incidencia.get().getFoto());
        response.getOutputStream().close();
    }

}
