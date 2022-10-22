package com.example.proy_grupo4.Controllers;


import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.AdminRepository;
import com.example.proy_grupo4.Repository.IconoRepository;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    IncidenciaRepository incidenciaRepository;

    @Autowired
    IconoRepository iconoRepository;

    @Autowired
    RolRepository rolRepository;
    @GetMapping(value = "/salir")
    public String Salir(){
        return "auth-login-basic";
    }

    @GetMapping(value = "/usuario")
    public String listar_usuario(Model model){
        model.addAttribute("lista_usuario",adminRepository.findAll());
        return "Admin_ListaUsuarios";
    }

    @GetMapping(value = "/incidentes")
    public String listar_incidentes(Model model){
        model.addAttribute("lista_incidentes", incidenciaRepository.findAll());
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
            attr.addFlashAttribute("msg", "Usuario creado exitosamente");
            usuariosRegistrado.setEstado("activo");
            usuariosRegistrado.setContrasena(BCrypt.hashpw("1234",BCrypt.gensalt()));
            usuariosRegistrado.setNumeroReportes(0);
        adminRepository.save(usuariosRegistrado);
        return "redirect:/admin/usuario";
    }

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
            usuariosRegistrado.setEstado("suspendido");
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
    public String Mapa(){
        return "Admin_MapaIncidencias";
    }

}
