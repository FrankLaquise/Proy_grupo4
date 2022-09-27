package com.example.proy_grupo4.Controllers;


import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.TodosLosUsuario;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.AdminRepository;
import com.example.proy_grupo4.Repository.IconoRepository;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.Repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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
        return "lista_usuario";
    }

    @GetMapping(value = "/incidentes")
    public String listar_incidentes(Model model){
        model.addAttribute("lista_incidentes", incidenciaRepository.findAll());
        return "incidencia";
    }

    @GetMapping(value = "/registrar_usuario")
    public String nuevo_usuario(@ModelAttribute("nuevo_usuario") UsuariosRegistrado usuariosRegistrado, Model model){
        model.addAttribute("lista_usuario", adminRepository.findAll());
        model.addAttribute("roles", rolRepository.findAll());
        model.addAttribute("icono", iconoRepository.findAll());
        return "registro";
    }

    @PostMapping("/save")
    public String Registro(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado, RedirectAttributes attr) {
        if (usuariosRegistrado.getId().isEmpty()) {
            attr.addFlashAttribute("msg", "Usuario creado exitosamente");
            usuariosRegistrado.setComentarioSuspension(" ");
            usuariosRegistrado.setEstado("activo");
            usuariosRegistrado.setContrasena(usuariosRegistrado.getId());
            usuariosRegistrado.setComentarioSuspension(" ");
            usuariosRegistrado.setNumeroReportes(0);
        } else {
            attr.addFlashAttribute("msg", "Usuario actualizado exitosamente");
        }
        adminRepository.save(usuariosRegistrado);
        return "redirect:/admin/usuario";
    }

    @GetMapping("/edit")
    public String editarUsuario(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado,
                                      Model model,@RequestParam("id") String id) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);
        if (optionalUsuariosRegistrado.isPresent()) {
            usuariosRegistrado = optionalUsuariosRegistrado.get();
            model.addAttribute("usuario", usuariosRegistrado);
            model.addAttribute("roles", rolRepository.findAll());
            return "usuario_activo";
        } else {
            return "redirect:/admin/usuario";
        }
    }


    @GetMapping("/perfil")
    public String perfil(){
            return "perfil_admin";

    }
    @GetMapping(value = "/mapa")
    public String Mapa(){
        return "mapa";
    }

}
