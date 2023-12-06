package com.example.spring.pago.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.spring.pago.model.Tarjeta;
import com.example.spring.pago.response.TarjetaResponse;

@FeignClient(name = "banco-service", url = "http://banco-env.eba-3zvamy8n.eu-west-3.elasticbeanstalk.com")
public interface BancoFeignClient {

    @GetMapping("/pasarela/validacion")
    public TarjetaResponse obtenerDatosValidacion(Tarjeta tarjeta);
}
