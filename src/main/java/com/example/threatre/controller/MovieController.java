package com.example.threatre.controller;

import com.example.threatre.dto.MovieRequestDTO;
import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.service.MovieService;
import com.example.threatre.dto.MoviesDTO;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filmes")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @PostMapping("/buscar")
    public ResponseEntity<MoviesAPIDTO> getAndSaveMovies(@RequestBody MovieRequestDTO movieRequestDTO) {
        if (movieRequestDTO.title() == null || movieRequestDTO.title().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do filme não pode ser vazio");
        }
        MoviesAPIDTO savedMovie = movieService.getAndSaveMovie(movieRequestDTO.title());
        return ResponseEntity.ok(savedMovie);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MoviesDTO> getMovieById(@PathVariable Long id) {
        MoviesDTO movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<MoviesDTO>> getAllMovies(){
        List<MoviesDTO> movies =  movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok("O filme foi deletado");
    }

}
