package com.example.threatre.service;

import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import com.example.threatre.exception.ResourceNotFoundException;
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

    public MoviesAPIDTO getAndSaveMovie(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("O título do filme não pode ser vazio");
        }

        MoviesAPIDTO foundMovie = getMoviesAPI(title);

        if (foundMovie == null) {
            throw new ResourceNotFoundException("Filme não encontrado");
        }

        Movie movieEntity = movieMapper.toEntity(foundMovie);

        movieRepository.save(movieEntity);

        return foundMovie;
    }


    private MoviesAPIDTO getMoviesAPI(String title) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search/movie")
                        .queryParam("query", title)
                        .build())
                .retrieve()
                .bodyToMono(MoviesResponseWrapperDTO.class)
                .map(response -> response.results().isEmpty() ? null : response.results().get(0))
                .block();
    }

    public List<MoviesDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies.stream()
                .map(movieMapper::toDto)
                .collect(Collectors.toList());
    }

    public MoviesDTO getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Filme com ID " + id + " não encontrado"));
        return movieMapper.toDto(movie);
    }


    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Filme com ID " + id + " não encontrado para exclusão");
        }
        movieRepository.deleteById(id);
    }

}






