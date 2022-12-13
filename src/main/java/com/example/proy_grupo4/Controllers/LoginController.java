package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Email;
import com.example.proy_grupo4.Entity.Roles;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.AdminRepository;
import com.example.proy_grupo4.Repository.UsuarioRepository;
import com.example.proy_grupo4.security.oauth.CustomOAuth2User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

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
    @GetMapping(value={"ventanaLogin", ""})
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
            System.out.println(rol);
        }

        String username = authentication.getName();//obtengo por correo
        UsuariosRegistrado usuariosRegistrado = usuarioRepository.findByCorreo(username);//busco por email
        session.setAttribute("usuario",usuariosRegistrado); 

        switch (rol){
            case "Administrativo" -> {return "redirect:/admin/incidentes?page=1&buscarx=horaCreacion";}
            case "Alumno" -> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case "Seguridad" -> {return "redirect:/seguridad/factor?id="+usuariosRegistrado.getId();}
            default -> {return"redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
        }
    }

    @GetMapping({"/oauth/login2"})
    public String logingooglept2(Authentication auth, Model model, HttpSession session) {
        String rol = "";
        List<GrantedAuthority> authorities=(List<GrantedAuthority>) auth.getAuthorities();
        for(GrantedAuthority grantedAuthority : authorities){
            rol=grantedAuthority.getAuthority();
        }
        UsuariosRegistrado userg = (UsuariosRegistrado) session.getAttribute("usuario");
        //Roles ro = (Roles) session.getAttribute("rol");
        System.out.println(userg.getNombre());
        Integer roluser = usuarioRepository.rolporid(userg.getId());
        System.out.println(roluser);
        switch (roluser){
            case (1)-> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case (2) -> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case (3)-> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case (4)-> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case (5)-> {return "redirect:/incidencia/list?page=1&buscarx=horaCreacion";}
            case (6) -> {return "redirect:/seguridad/factor?id="+userg.getId();}
            case (7) -> {return "redirect:/admin/incidentes?page=1&buscarx=horaCreacion\"";}
            default -> {return"redirect:/incidencia/list?page=1&buscarx=horaCreacion\"";}
        }
    }
    @GetMapping("/oauth2/login")
    public String redirectOauth2(Model model, OAuth2AuthenticationToken authentication, HttpSession session){
        OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);

        Map userAttributes = Collections.emptyMap();
        String userInfoEndpointUri = authorizedClient.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        userAttributes = WebClient.builder().filter(oauth2Credentials(authorizedClient))
                .build().get().uri(userInfoEndpointUri).retrieve().bodyToMono(Map.class).block();

        String email = (String) userAttributes.get("email");

        System.out.println(email);
        Optional<UsuariosRegistrado> optusuario = Optional.ofNullable(usuarioRepository.findByCorreo(email));
        if(optusuario.isPresent()){
            UsuariosRegistrado usuariop = optusuario.get();
            session.setAttribute("usuario",usuariop);
            session.setAttribute("rol",usuariop.getRol());
            System.out.println(usuariop.getCorreo());
            System.out.println(usuariop.getNombre());
            System.out.println(usuariop.getDni());
            return "redirect:/oauth/login2";
        }else{
            System.out.println("usuario no existe en base de datos");
            return "redirect:/errorLoginGoogle";
        }
    }


    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

    private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
        return ExchangeFilterFunction.ofRequestProcessor(
                clientRequest -> {
                    ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
                            .header(HttpHeaders.AUTHORIZATION, "Bearer " +
                                    authorizedClient.getAccessToken().getTokenValue()).build();
                    return Mono.just(authorizedRequest);
                });
    }

    @GetMapping(value = {"/errorLoginGoogle"})
    public String errorLoginGoogle(){
        return "Error_loginGoogle";
    }
}
