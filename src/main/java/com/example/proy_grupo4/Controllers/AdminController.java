package com.example.proy_grupo4.Controllers;


import com.example.proy_grupo4.Email;
import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.Sugerencia;
import com.example.proy_grupo4.Entity.TodosLosUsuario;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.service.api.IncidenciaServiceAPI;
import com.example.proy_grupo4.service.impl.NewIncidenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    SugerenciaRepository sugerenciaRepository;
    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Autowired
    IconoRepository iconoRepository;

    @Autowired
    RolRepository rolRepository;

    @Autowired
    private Email sender;

    public void sendMail(String destino, String subjet, String body){
        sender.sendEmail(destino,subjet,body);
    }
    @GetMapping(value = "/salir")
    public String Salir(){
        return "auth-login-basic";
    }

    @GetMapping(value = "/usuario")
    public String listar_usuario(Model model){
        model.addAttribute("lista_usuario",adminRepository.findAll());
        return "Admin_ListaUsuarios";
    }

    //paginacion_INICIO...
    @Autowired
    private IncidenciaServiceAPI incidenciaServiceAPI;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private NewIncidenciaService newIncidenciaService;
    @GetMapping(value = {"/incidentes"})
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
            model.addAttribute("lista_incidentes",pageIncidencia.getContent());
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
            model.addAttribute("lista_incidentes",pageIncidencia.getContent());
            model.addAttribute("current",page+1);
            model.addAttribute("next",page+2);
            model.addAttribute("prev",page);
            model.addAttribute("last",totalPage);

            model.addAttribute("ordenarpor","horaCreacion");
        }
        return "Admin_ListaIncidencias";
    }

    @GetMapping(value = "/registrar_usuario")
    public String nuevo_usuario(@ModelAttribute("nuevo_usuario") UsuariosRegistrado usuariosRegistrado, Model model){
        model.addAttribute("lista_usuario", adminRepository.findAll());
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("icono", iconoRepository.findAll());
        return "Admin_Registro";
    }

    @PostMapping("/save")
    public String Registro(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado, RedirectAttributes attr) {
        usuariosRegistrado.setComentarioSuspension("Falta ser activado");
            usuariosRegistrado.setEstado("0");
            usuariosRegistrado.setContrasena(BCrypt.hashpw("1234",BCrypt.gensalt()));
            usuariosRegistrado.setNumeroReportes(0);
            if(usuariosRegistrado.getRol().getId()==6){
                sender.sendEmail(usuariosRegistrado.getCorreo(),"Link para activar la cuenta",
                        "https://nohaycreatividad.azurewebsites.net/activacion?id="+usuariosRegistrado.getId() +"\n la clave es 1234");
            }
        adminRepository.save(usuariosRegistrado);
        return "redirect:/admin/usuario";
    }

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/actualizar")
    public String Actualizar(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado) {
        if(usuariosRegistrado.getComentarioSuspension().isEmpty()){
            usuariosRegistrado.setComentarioSuspension("activo");
            adminRepository.save(usuariosRegistrado);
            return "redirect:/admin/usuario";
        } else if (usuariosRegistrado.getComentarioSuspension().equals("activo")) {
            adminRepository.save(usuariosRegistrado);
            return "redirect:/admin/usuario";
        }else{
            usuariosRegistrado.setEstado("0");
            if(usuariosRegistrado.getRol().getId()==6){
                sender.sendEmail(usuariosRegistrado.getCorreo(),"Cuenta suspendida",
                        "Estimado: \n Su cuenta ha sido suspendida por el sigueinte motivo:" +
                        usuariosRegistrado.getComentarioSuspension());
            }
            adminRepository.save(usuariosRegistrado);
            return "redirect:/admin/usuario";
        }
        }

    @GetMapping("/edit")
    public String editarUsuario(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado,
                                      Model model,@RequestParam("id") String id) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);
        if (optionalUsuariosRegistrado.isPresent()) {
            usuariosRegistrado = optionalUsuariosRegistrado.get();
            if(usuariosRegistrado.getEstado().equals("1")){
                model.addAttribute("usuario", usuariosRegistrado);
                model.addAttribute("roles", rolRepository.findAll());
                return "usuario_activo";
            }else{
                model.addAttribute("usuario", usuariosRegistrado);
                model.addAttribute("roles", rolRepository.findAll());
                return "usuario_suspendido";
            }
        } else {
            return "redirect:/admin/usuario";
        }
    }

    @GetMapping("/activar")
    public String activarUsuario(@RequestParam("id") String id, Model model) {
        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);
        UsuariosRegistrado usuariosRegistrado = optionalUsuariosRegistrado.get();
        usuariosRegistrado.setEstado("1");
        usuariosRegistrado.setComentarioSuspension("activo");
        adminRepository.save(usuariosRegistrado);
        model.addAttribute("usuario", usuariosRegistrado);
        model.addAttribute("roles", rolRepository.findAll());
        return "usuario_activo";
    }

    @GetMapping("/perfil")
    public String perfil(){
            return "Admin_Perfil";

    }
    @GetMapping(value = "/mapa")
    public String Mapa(Model model){
        List<Incidencia> incidencia = incidenciaRepository.findAll();
        List<BigDecimal> latitud = new ArrayList();
        List<BigDecimal> longitud = new ArrayList();
        List<Integer> icono = new ArrayList();
        for(Incidencia inci : incidencia){
            latitud.add(inci.getLatitud());
            longitud.add(inci.getLongitud());
            icono.add(inci.getIcono().getId());
        }
        model.addAttribute("latitud",latitud);
        model.addAttribute("longitud",longitud);
        model.addAttribute("icono",icono);
        return "Admin_MapaIncidencias";
    }

    @GetMapping("/sugerencias")
    public String Sugerencia(Model model){
        List<Sugerencia> listadesugenrencias=sugerenciaRepository.findAll();
        model.addAttribute("listadesugerencias",listadesugenrencias);
        return "Admin_Sugerencias";}
}
