package com.movCast.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebServiceConfig {

    @Bean
    public WebClient webclient()
    {
        return WebClient.builder().build();
    }
}
