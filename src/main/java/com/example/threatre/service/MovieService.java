package com.example.threatre.service;

import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import com.example.threatre.mapper.MovieMapper;
import com.example.threatre.model.Movie;
import com.example.threatre.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Autowired
    private WebClient webClient;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMapper movieMapper;


    public MoviesDTO getMovieById(Long id) {
       return movieMapper.toDto(movieRepository.findById(id).get());

    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public MoviesAPIDTO getAndSaveMovie(String titulo) {
        MoviesAPIDTO filmeEncontrado = webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", titulo)
                        .build())
                .retrieve()
                .bodyToMono(MoviesResponseWrapperDTO.class)
                .map(response -> response.results().isEmpty() ? null : response.results().get(0))
                .block();

        if (filmeEncontrado == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado");
        }

        Movie movieEntity = movieMapper.toEntity(filmeEncontrado);



        movieRepository.save(movieEntity);

        return filmeEncontrado;
    }

    public List<MoviesDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

}






