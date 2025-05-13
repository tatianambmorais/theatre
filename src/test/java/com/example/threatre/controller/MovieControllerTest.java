package com.example.threatre.controller;

import com.example.threatre.dto.MovieRequestDTO;
import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.exception.ResourceNotFoundException;
import com.example.threatre.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAndSaveMovies_sucsess() throws Exception {
        MoviesAPIDTO movie = new MoviesAPIDTO(1L, "Avatar", "Avatar", "en",
                "Um filme de ficção", "poster.jpg", "backdrop.jpg",
                "2009-12-18", 7.8, 8.5, 1500, false, false, List.of(28, 12));

        MovieRequestDTO requestDTO = new MovieRequestDTO("Avatar");

        Mockito.when(movieService.getAndSaveMovie("Avatar")).thenReturn(movie);

        mockMvc.perform(post("/filmes/buscar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Verifica o tipo de conteúdo
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Avatar"))
                .andExpect(jsonPath("$.overview").value("Um filme de ficção"));
    }

    @Test
    void testGetMovieById_success() throws Exception {
        MoviesDTO movieDTO = new MoviesDTO(1L, "Avatar", "Um filme de ficção");

        Mockito.when(movieService.getMovieById(1L)).thenReturn(movieDTO);

        mockMvc.perform(get("/filmes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Verifica o tipo de conteúdo
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Avatar"))
                .andExpect(jsonPath("$.overview").value("Um filme de ficção"));
    }


    @Test
    void testGetMovieById_notFound() throws Exception {
        Mockito.when(movieService.getMovieById(1L))
                .thenThrow(new ResourceNotFoundException("Filme não encontrado"));

        mockMvc.perform(get("/filmes/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // Verifica o tipo de conteúdo
                .andExpect(jsonPath("$.error").value("Recurso não encontrado"))
                .andExpect(jsonPath("$.message").value("Filme não encontrado"));
    }


    @Test
    void testGetAllMovies() throws Exception {
        MoviesDTO movie1 = new MoviesDTO(1L, "Avatar", "Um filme de ficção");
        MoviesDTO movie2 = new MoviesDTO(2L, "Titanic", "Um romance no navio");

        Mockito.when(movieService.getAllMovies()).thenReturn(List.of(movie1, movie2));

        mockMvc.perform(get("/filmes/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Avatar"))
                .andExpect(jsonPath("$[1].title").value("Titanic"));
    }

    @Test
    void testDeleteMovie_success() throws Exception {
        mockMvc.perform(delete("/filmes/deletar/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("O filme foi deletado"));

        Mockito.verify(movieService).deleteMovie(1L);
    }
}
