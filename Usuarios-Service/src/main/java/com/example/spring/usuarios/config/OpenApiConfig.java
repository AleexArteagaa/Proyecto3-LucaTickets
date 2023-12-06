package com.example.spring.usuarios.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class OpenApiConfig {
	
    @Bean
    public OpenAPI UsuarioOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("LucaTicket API Usuarios")
                .description("Documentación de la API de LucaTick, microservicio Usuarios")
                .version("v1.0")
                .contact(new Contact().name("Alejandro Arteaga, Cristian Serrano, Alejandro Cabrera y Jonay Sanchez").
                        url("https://lucasteam.es").email("lucasteam@gmail.es"))
                .license(new License().name("LICENSE").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                .description("Descripcion del proyecto")
                .url("https://lucaticket.es"));
    }
	

}
