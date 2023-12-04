package com.example.spring.eventos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventosServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventosServiceApplication.class, args);
	}

}
