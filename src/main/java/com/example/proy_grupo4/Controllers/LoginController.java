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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AdminRepository adminRepository;

    @PostMapping("activar")
    public String Activar(UsuariosRegistrado usuario) {
        usuario.setComentarioSuspension("activo");
        usuario.setEstado("1");
        String contra = usuario.getContrasena();
        usuario.setContrasena(BCrypt.hashpw(contra,BCrypt.gensalt()));
        adminRepository.save(usuario);
        return "redirect:/admin/usuario";
    }

    @PostMapping("recuperar")
    public String Recuperar(UsuariosRegistrado usuario) {
        String contra = usuario.getContrasena();
        usuario.setContrasena(BCrypt.hashpw(contra,BCrypt.gensalt()));
        adminRepository.save(usuario);
        return "redirect:/admin/usuario";
    }
    @GetMapping("ventanaLogin")
    public String ventanaLogin(){
        return "Login";
    }

    @GetMapping("activacion")
    public String activacion(Model model,@RequestParam("id") String id){
        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = adminRepository.findById(id);
        UsuariosRegistrado usuariosRegistrado = optionalUsuariosRegistrado.get();
        model.addAttribute("usuario",usuariosRegistrado);
        return "ACTIVACION";
    }

    @Autowired
    private Email sender;
    @PostMapping("olvido")
    public String olvidar(@RequestParam("corre") String correo){
        sender.sendEmail(correo,"Link para la recuperacion del clave",
                "https://nohaycreatividad.azurewebsites.net/recuperacion?correo="+correo);
        return "Login";
    }

    @GetMapping("recuperacion")
    public String recu(Model model, @RequestParam("correo") String correo){
        UsuariosRegistrado usuario = adminRepository.buscarxcorreo(correo);
        model.addAttribute("usuario",usuario);
        return "Login_RecuperarContra";
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
