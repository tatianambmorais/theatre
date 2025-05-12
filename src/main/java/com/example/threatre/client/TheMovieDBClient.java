package com.example.threatre.client;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TheMovieDBClient {
    private final WebClient webClient;

    @Autowired
    public TheMovieDBClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
