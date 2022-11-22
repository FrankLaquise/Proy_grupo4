package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Email;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.AdminRepository;
import com.example.proy_grupo4.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("activar")
    public String Activar(UsuariosRegistrado usuariosRegistrado) {
        UsuariosRegistrado usuario = adminRepository.buscarxcorreo(usuariosRegistrado.getCorreo());
        usuario.setComentarioSuspension("activo");
        usuario.setEstado("activo");
        usuario.setContrasena(BCrypt.hashpw(usuariosRegistrado.getContrasena(),BCrypt.gensalt()));
        adminRepository.save(usuario);
        return "redirect:/admin/usuario";
    }
    @GetMapping("ventanaLogin")
    public String ventanaLogin(){
        return "Login";
    }

    @GetMapping("activacion")
    public String activacion(){
        return "ACTIVACION";
    }

    @GetMapping("redireccionarPorRol")
    public String redireccionarPorRol(Authentication authentication, HttpSession session){
        String rol ="";
        List<GrantedAuthority> authorities=(List<GrantedAuthority>) authentication.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            System.out.println(grantedAuthority);
            rol=grantedAuthority.getAuthority();
        }

        String username = authentication.getName();//obtengo por correo
        UsuariosRegistrado usuariosRegistrado = usuarioRepository.findByCorreo(username);//busco por email
        session.setAttribute("usuario",usuariosRegistrado);

        switch (rol){
            case "Administrativo" -> {return "redirect:/admin/incidentes";}
            case "Alumno" -> {return "redirect:/incidencia/list";}
            case "Seguridad" -> {return "redirect:/seguridad/factor?id="+usuariosRegistrado.getId();}
            default -> {return"redirect:/incidencia/list";}
        }
    }

}
