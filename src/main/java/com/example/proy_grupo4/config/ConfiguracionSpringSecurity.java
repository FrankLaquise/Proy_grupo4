package com.example.proy_grupo4.config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class ConfiguracionSpringSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/ventanaLogin").loginProcessingUrl("/procesarLoginForm")
                .defaultSuccessUrl("/redireccionarPorRol", true);
//Alumno,Administrativo,Jefe de Práctica
        //Profesor,Egresado,Seguridad
        //Administrador
        http.authorizeRequests()
                .antMatchers("/admin","/admin/**").hasAuthority("Administrativo")
                .antMatchers("/incidencia","/incidencia/**").hasAuthority("Alumno")
                .antMatchers("/seguridad","/seguridad/**").hasAuthority("Seguridad")
                .anyRequest().permitAll();
        http.logout()
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);//cuando cierre sesion,borra la cookie e invalida sesion
    }

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("SELECT correo,contrasena,estado FROM reportpucp.usuarios_registrados where correo=?")
                .authoritiesByUsernameQuery("SELECT correo,r.titulo FROM usuarios_registrados u inner join roles r on (u.rol = r.idroles) where correo=? and u.estado=1;");

    }
}