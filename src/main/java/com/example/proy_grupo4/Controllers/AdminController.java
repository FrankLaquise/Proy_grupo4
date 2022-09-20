package com.example.proy_grupo4.Controllers;


import com.example.proy_grupo4.Entity.Incidencia;
import com.example.proy_grupo4.Entity.TodosLosUsuario;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.AdminRepository;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
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

    @GetMapping(value ={ "/usuario"})
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
    public String nuevo_usuario(@ModelAttribute("nuevo_usuario") TodosLosUsuario todosLosUsuario, Model model){
        model.addAttribute("lista_usuario", adminRepository.findAll());
        return "registro";
    }

    @PostMapping("/save")
    public String guardarProducto(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado, RedirectAttributes attr) {
        if (usuariosRegistrado.getId().isEmpty()) {
            attr.addFlashAttribute("msg", "Usuario creado exitosamente");
        } else {
            attr.addFlashAttribute("msg", "Usuario actualizado exitosamente");
        }
        adminRepository.save(usuariosRegistrado);
        return "redirect:/admin";
    }

    @GetMapping("/edit")
    public String editarUsuario(@ModelAttribute("usuario") UsuariosRegistrado usuariosRegistrado,
                                      Model model,@RequestParam("id") int id) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);

        if (optionalUsuariosRegistrado.isPresent()) {
            usuariosRegistrado = optionalUsuariosRegistrado.get();
            model.addAttribute("usuario", usuariosRegistrado);
            return "usuario_activo";
        } else {
            return "redirect:/admin";
        }
    }

    @GetMapping("/delete")
    public String borrarUsuario(Model model,
                                      @RequestParam("id") int id,
                                      RedirectAttributes attr) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);

        if (optionalUsuariosRegistrado.isPresent()) {
            adminRepository.deleteById(id);
            attr.addFlashAttribute("msg","Usuario borrado exitosamente");
        }
        return "redirect:/admin";
    }

    @GetMapping("/perfil")
    public String perfil(@ModelAttribute("admin") UsuariosRegistrado usuariosRegistrado,
                                Model model,@RequestParam("id") int id) {

        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);

        if (optionalUsuariosRegistrado.isPresent()) {
            usuariosRegistrado = optionalUsuariosRegistrado.get();
            model.addAttribute("admin", usuariosRegistrado);
            return "perfil_admin";
        } else {
            return "redirect:/admin";
        }
    }

}
