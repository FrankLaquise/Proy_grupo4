package com.example.proy_grupo4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ProyGrupo4Application {

    @Autowired
    private Email sender;

    public static void main(String[] args) {
        SpringApplication.run(ProyGrupo4Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail(){
        sender.sendEmail("a20190212@pucp.edu.pe","aaa","pruebne");
    }

}
