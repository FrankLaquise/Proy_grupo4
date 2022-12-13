package com.example.proy_grupo4.Controllers;

import com.example.proy_grupo4.Email;
import com.example.proy_grupo4.Excel;
import com.example.proy_grupo4.PDF;
import com.example.proy_grupo4.Entity.Comentario;
import com.example.proy_grupo4.Entity.UsuariosRegistrado;
import com.example.proy_grupo4.Repository.*;
import com.example.proy_grupo4.Repository.IncidenciaRepository;
import com.example.proy_grupo4.service.api.IncidenciaServiceAPI;
import com.example.proy_grupo4.service.impl.NewIncidenciaService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.proy_grupo4.Entity.Incidencia;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    UsuarioRepository usuarioRepository;

    @GetMapping(value = {"/login2F"})
    public String Seguridadlogin2F(){
        return "Seguridad_login2F";
    }

    //Para la vista de inicio
    @Autowired
    private IncidenciaServiceAPI incidenciaServiceAPI;

    @Autowired
    private NewIncidenciaService newIncidenciaService;

    @Autowired
    private Email sender;

    @Autowired
    UsuarioRepository usuarioRepository2;
    @PostMapping(value = {"/cambiotel"})
    public String usuariocambiotel(UsuariosRegistrado usuario, @RequestParam("id") String id){
        Optional<UsuariosRegistrado> opt = usuarioRepository2.findById(id);
        if (opt.isPresent()) {  usuarioRepository2.actualizarTelefono(usuario.getTelefono(),id);
        }
        return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";}
    public void sendMail(String destino, String subjet, String body){
        sender.sendEmail(destino,subjet,body);
    }
    @GetMapping(value = {"/inicio",""})
    public String aaaa(@RequestParam(name="buscarx" , required = false) String buscarx,@RequestParam Map<String,Object> params, Model model){
        int page = params.get("page") != null ?(Integer.valueOf(params.get("page").toString())-1):0;
        PageRequest pageRequest =PageRequest.of(page,3);
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
        List<Incidencia> listita = incidenciaRepository.findAll();
        String texto = "Titulo        Urgencia   Fecha         Zona       Tipo       Estado\n";
        for(Incidencia inci : listita){
            String hora = String.valueOf(inci.getHoraCreacion());
            String charsToRemove = "TZ";
            for (char c : charsToRemove.toCharArray()) {
                hora = hora.replace(String.valueOf(c), " ");
            }
            texto = texto + inci.getTitulo() + "   " + inci.getNivel() + "    " + hora +
                    "    " + inci.getZona().getTitulo() + "   "  + inci.getTipo().getTitulo() + "    "
                    + inci.getEstado() + "\n";
        }
        model.addAttribute("listita",texto);
        return "Seguridad_ListaIncidencias2";
    }


    @GetMapping(value = {"/exportarpdf"})
    public void exportarListadoDeEmpleadosEnPDF(HttpServletResponse response) throws DocumentException, IOException, IOException {
        response.setContentType("application/pdf");
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=lista.pdf";
        response.setHeader(cabecera, valor);
        List<Incidencia> incidencias = incidenciaRepository.findAll();
        PDF exporter = new PDF(incidencias);
        exporter.exportar(response);
    }

    @GetMapping(value = {"/exportarExcel"})
    public void exportarListadoEnExcel(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/octet-stream");
        String cabecera = "Content-Disposition";
        String valor = "attachment; filename=Incidencia.xlsx";
        response.setHeader(cabecera, valor);
        List<Incidencia> incidencias = incidenciaRepository.findAll();
        Excel exporter = new Excel(incidencias);
        exporter.exportar(response);
    }
    @GetMapping(value = {"/dashboard"})
    public String SeguridadDashboard(Model model){
        List<Incidencia> listaincidencia = incidenciaRepository.findAll();
        int robo = 0;
        int perdido = 0;
        int ambulante = 0;
        int accidente = 0;
        int infra = 0;
        int otro = 0;
        int total = 0;
        int zona1 = 0;
        int zona2 = 0;
        int zona3 = 0;
        int zona4 = 0;
        int zona5 = 0;
        int zona6 = 0;
        int nivel1 = 0;
        int nivel2 = 0;
        int nivel3 = 0;
        int cal1 = 0;
        int cal2 = 0;
        int cal3 = 0;
        int cal4 = 0;
        int cal5 = 0;
        int resuelto = 0;
        int proceso = 0;
        int atendido = 0;
        int registrado = 0;
        for(Incidencia incidencia : listaincidencia){
            if(incidencia.getTipo().getId()==1){
                robo ++;
            } else if (incidencia.getTipo().getId()==2) {
                perdido ++;
            } else if(incidencia.getTipo().getId()==3){
                infra ++;
            } else if (incidencia.getTipo().getId()==4) {
                ambulante ++;
            } else if (incidencia.getTipo().getId()==5) {
                accidente ++;
            } else if (incidencia.getTipo().getId()==6) {
                otro ++;
            }
            if(incidencia.getZona().getId()==1){
                zona1 ++;
            } else if (incidencia.getZona().getId()==2) {
                zona2 ++;
            } else if(incidencia.getZona().getId()==3){
                zona3 ++;
            } else if (incidencia.getZona().getId()==4) {
                zona4 ++;
            } else if (incidencia.getZona().getId()==5) {
                zona5 ++;
            } else {
                zona6 ++;
            }
            if(incidencia.getNivel().equals("Leve")){
                nivel1 ++;
            } else if (incidencia.getNivel().equals("Moderado")) {
                nivel2 ++;
            } else{
                nivel3 ++;
            }
            if(incidencia.getCalificacion()==1){
                cal1 ++;
            } else if (incidencia.getCalificacion()==2) {
                cal2 ++;
            }else if (incidencia.getCalificacion()==3) {
                cal3 ++;
            }else if (incidencia.getCalificacion()==4) {
                cal4 ++;
            }else if (incidencia.getCalificacion()==5) {
                cal5 ++;
            }
            if(incidencia.getEstado().equals("resuelto")){
                resuelto ++;
            } else if (incidencia.getEstado().equals("en proceso")) {
                proceso ++;
            } else if (incidencia.getEstado().equals("atendido")) {
                atendido ++;
            } else if (incidencia.getEstado().equals("registrado")) {
                registrado ++;
            }
            total ++;
        }
        model.addAttribute("robo",robo*100/total);
        model.addAttribute("perdido",perdido*100/total);
        model.addAttribute("ambulante",ambulante*100/total);
        model.addAttribute("accidente",accidente*100/total);
        model.addAttribute("infra",infra*100/total);
        model.addAttribute("otro",otro*100/total);
        model.addAttribute("zona1",zona1*100/total);
        model.addAttribute("zona2",zona2*100/total);
        model.addAttribute("zona3",zona3*100/total);
        model.addAttribute("zona4",zona4*100/total);
        model.addAttribute("zona5",zona5*100/total);
        model.addAttribute("zona6",zona6*100/total);
        model.addAttribute("leve",nivel1*100/total);
        model.addAttribute("moderado",nivel2*100/total);
        model.addAttribute("critico",nivel3*100/total);
        model.addAttribute("cal1",cal1);
        model.addAttribute("cal2",cal2);
        model.addAttribute("cal3",cal3);
        model.addAttribute("cal4",cal4);
        model.addAttribute("cal5",cal5);
        model.addAttribute("atendido",atendido);
        model.addAttribute("resuelto",resuelto);
        model.addAttribute("registrado",registrado);
        model.addAttribute("proceso",proceso);
        model.addAttribute("promedio",(cal5*5+cal4*4+cal3*3+cal2*2+cal1)/(cal5+cal4+cal3+cal2+cal1));
        return "Seguridad_Dashboard";
    }
    @GetMapping(value = {"/mapa"})
    public String SeguridadMapa(Model model){
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
        return "Seguridad_MapaIncidencias";
    }

    @GetMapping(value = {"/factor"})
    public String factor(Model model, @RequestParam String id){
        Optional<UsuariosRegistrado> opt = adminRepository.findById(id);
        UsuariosRegistrado usuariosRegistrado = opt.get();
        int codigo = (int)(Math.random()*(9999-1000+1)+1000);
        sender.sendEmail(usuariosRegistrado.getCorreo(),"Codigo de doble factor",
                "El codigo de verificacion es el siguiente:\n"+ codigo);
        model.addAttribute("codigo",codigo);
        return "verificacion";
    }

    //Para ver el detalle de las incidencias
    @GetMapping(value = {"/detalle"})
    public String SeguridadDetalle(Model model, @RequestParam("id") int id){
        Optional<Incidencia> optInc = incidenciaRepository.findById(id);
        String codigoCreador = incidenciaRepository.buscarCreador(id);

        //List<String> comentarios= comentariosRepository.ComentariosporidInc(id);
        List<Comentario> comentarios = comentariosRepository.ComentariosporidInci(id);
        if(optInc.isPresent()){
            Incidencia incidencia=optInc.get();
            model.addAttribute("comentarios",comentarios);
            model.addAttribute("incidencia",incidencia);
            model.addAttribute("codigocreador",codigoCreador);
            model.addAttribute("icono",incidencia.getIcono().getId());
            return "Seguridad_IncidenciaDetalle";
        }else{
            return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
        }
    }
    @PostMapping(value = {"/actualizarest"})
    public String Actualizaresst(Model model, Incidencia incidencia, String comentario, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            incidenciaRepository.Actualizar(id, incidencia.getEstado());
            if(comentario!=null) {
                if(comentario.length() != 0) {
                    comentariosRepository.IngresarComentxIdinci(comentario, id, "seguridad", Instant.now());
                }
            }

        }
        System.out.println(comentario);
        return  "redirect:/seguridad/detalle?id="+id;
    }

    @GetMapping(value = {"/reportar"})
    public String Aumentarrep(Incidencia incidencia, @RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            String codigoCreador = incidenciaRepository.buscarCreador(id);
            Optional<UsuariosRegistrado> usuariosRegistrado = adminRepository.findById(String.valueOf(id));
            UsuariosRegistrado usuario = usuariosRegistrado.get();
            if(usuario.getNumeroReportes()==2){
                sender.sendEmail(usuario.getCorreo(),"Cuenta suspendida","Estimado.\n"+
                        "Su cuenta ha sido suspendido por poseer mas de 3 reportes");
            }
            seguridadRepository.aumentarreportes(codigoCreador);
            seguridadRepository.ValidarSuspenderusuario(codigoCreador);
            incidenciaRepository.ReportarFalsaIncidencia(id);
        }
        return  "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
    }

    @PostMapping(value = {"/reportarinc"})
    public String Aumentarrepp(Model model, Incidencia incidencia, String comentarioreporte,@RequestParam("id") int id){
        Optional<Incidencia> opt = incidenciaRepository.findById(id);
        if(opt.isPresent()){
            String codigoCreador = incidenciaRepository.buscarCreador(id);
            seguridadRepository.aumentarreportes(codigoCreador);
            seguridadRepository.ValidarSuspenderusuario(codigoCreador);
            incidenciaRepository.ReportarFalsaIncidencia(id);
            if(comentarioreporte != null) incidenciaRepository.ReportarFalsaIncidenciacoment(id,comentarioreporte);
        }
        return  "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, HttpSession session) {
        UsuariosRegistrado user = (UsuariosRegistrado) session.getAttribute("usuario");
        Optional<UsuariosRegistrado> optionalUsuariosRegistrado = seguridadRepository.findById(user.getId());
        if (optionalUsuariosRegistrado.isPresent()) {
            UsuariosRegistrado seguridad= optionalUsuariosRegistrado.get();
            model.addAttribute("usuario", seguridad);
            return "Seguridad_Perfil";
        } else {
            return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
        }
    }

    @GetMapping(value = {"/actualizaratendido"})
    public String SeguridadActualizar1(@RequestParam("id") int id){
        Optional<Incidencia> incidencia = incidenciaRepository.findById(id);
        Incidencia inci = incidencia.get();
        Optional<UsuariosRegistrado> usuario = adminRepository.findById(inci.getUsuario());
        UsuariosRegistrado usuariosRegistrado = usuario.get();
        sender.sendEmail(usuariosRegistrado.getCorreo(),"Incidencia" + inci.getTitulo()+" actualizado",
                "Estimado, su incidencia " + inci.getTitulo() + "fue atendida, podra revisarlo");
        inci.setEstado("atendido");
        incidenciaRepository.save(inci);
        comentariosRepository.IngresarComentxIdinci("atendido", id, "seguridad", Instant.now());
        return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
    }
    @GetMapping(value = {"/actualizarenproceso"})
    public String SeguridadActualizar2(@RequestParam("id") int id){
        incidenciaRepository.ActualizarEnproceso(id);
        return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
    }
    @GetMapping(value = {"/actualizarregistrado"})
    public String SeguridadActualizar3(@RequestParam("id") int id){
        incidenciaRepository.ActualizarRegistrado(id);
        return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
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
                return "Error_404";
            }
        }
        usuariosRegistrado.setTelefono(usuario.getTelefono());
        usuarioRepository.actualizar(usuariosRegistrado.getTelefono(),id);
        return "redirect:/seguridad/inicio?page=1&buscarx=horaCreacion";
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


