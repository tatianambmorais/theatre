package com.example.threatre.controller;

import com.example.threatre.service.MovieService;
import com.example.threatre.dto.MoviesDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/em-cartaz")
    public Mono<ResponseEntity<List<MoviesDTO>>> getNowPlayingMovies() {
        return movieService.getNowPlayingMovies()
                .map(ResponseEntity::ok);
    }
    @GetMapping("/{id}")
    public MoviesDTO getMovieById(@PathVariable  Long id) {
        return movieService.getMovieById(id);
    }
}
