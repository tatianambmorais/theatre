package com.example.threatre.service;

import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import com.example.threatre.mapper.MovieMapper;
import com.example.threatre.model.Movie;
import com.example.threatre.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class MovieService {
    @Autowired
    private WebClient webClient;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieMapper movieMapper;

    public Mono<List<MoviesDTO>> getNowPlayingMovies() {
        return webClient
                .get()
                .uri("/movie/now_playing")
                .retrieve()
                .bodyToMono(MoviesResponseWrapperDTO.class)
                .map(MoviesResponseWrapperDTO::results)
                .doOnNext(this::saveList);

    }
    @Transactional
    public void saveList(List<MoviesDTO> filmes) {
        List<Movie> filmesToSave = filmes.stream()
                .map(movieDTO -> {
                    Movie movie = movieMapper.toEntity(movieDTO);
                    return movie;
                })
                .toList();

        System.out.println(filmesToSave);
        movieRepository.saveAll(filmesToSave);
    }

    public MoviesDTO getMovieById(Long id) {
       return movieMapper.toDto(movieRepository.findById(id).get());

    }
}






