package com.example.threatre.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${tmbi.api.token}")
    private String apiToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> template.query("api_key", apiToken);
    }
}
