package com.example.threatre.service;


import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import com.example.threatre.exception.ResourceNotFoundException;
import com.example.threatre.mapper.MovieMapper;
import com.example.threatre.model.Movie;
import com.example.threatre.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.function.Function;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private WebClient webClient;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAndSaveMovie_success() {
        // Arrange
        String title = "Avatar";
        MoviesResponseWrapperDTO responseWrapper = new MoviesResponseWrapperDTO(
                List.of(new MoviesAPIDTO(1L, title, title, "en", "Um filme de ficção",
                        "poster.jpg", "backdrop.jpg", "2009-12-18", 7.8, 8.5,
                        1500, false, false, List.of(28, 12))));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(MoviesResponseWrapperDTO.class))
                .thenReturn(Mono.just(responseWrapper));

        when(movieMapper.toEntity(any())).thenReturn(new Movie());
        when(movieRepository.save(any())).thenReturn(new Movie());

        MoviesAPIDTO result = movieService.getAndSaveMovie(title);

        assertNotNull(result);
        assertEquals("Avatar", result.title());
    }
    @Test
    void testGetAndSaveMovie_movieNotFound() {
        String title = "Inexistente";

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(MoviesResponseWrapperDTO.class)).thenReturn(Mono.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getAndSaveMovie(title));
    }


    private MoviesAPIDTO criarMoviesAPIDTOFake() {
        return new MoviesAPIDTO(
                1L,                      // esse é o id
                "Avatar",
                "Avatar",
                "en",
                "Um filme de ficção",
                "poster.jpg",
                "backdrop.jpg",
                "2009-12-18",
                7.8,
                8.5,
                1500,
                false,
                false,
                List.of(28, 12)
        );
    }

    @Test
    void testGetAndSaveMovie_empyTitle() {
        assertThrows(IllegalArgumentException.class, () -> movieService.getAndSaveMovie("  "));
    }

    @Test
    void testGetAndSaveMovie_nullTitle() {
        assertThrows(IllegalArgumentException.class, () -> movieService.getAndSaveMovie(null));
    }

    @Test
    void testGetAllMovies_success() {
        List<Movie> movies = List.of(new Movie(), new Movie());
        when(movieRepository.findAll()).thenReturn(movies);
        when(movieMapper.toDto(any(Movie.class))).thenReturn(new MoviesDTO(1L, "Avatar", "Um filme de ficção"));

        List<MoviesDTO> result = movieService.getAllMovies();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(movieRepository).findAll();
        verify(movieMapper, times(2)).toDto(any(Movie.class));
    }

    @Test
    void testGetMovieById_success() {
        Movie movie = new Movie();
        movie.setId(1L);
        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.of(movie));
        when(movieMapper.toDto(any(Movie.class))).thenReturn(new MoviesDTO(1L, "Avatar", "Um filme de ficção"));

        MoviesDTO result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
        verify(movieRepository).findById(1L);
        verify(movieMapper).toDto(movie);
    }

    @Test
    void testGetMovieById_movieNotFound() {
        when(movieRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> movieService.getMovieById(1L));
        verify(movieRepository).findById(1L);
    }

    @Test
    void testDeleteMovie_sucsess() {
        when(movieRepository.existsById(1L)).thenReturn(true);
        doNothing().when(movieRepository).deleteById(1L);

        movieService.deleteMovie(1L);

        verify(movieRepository).existsById(1L);
        verify(movieRepository).deleteById(1L);
    }

    @Test
    void testDeleteMovie_notFound() {
        when(movieRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> movieService.deleteMovie(1L));
        verify(movieRepository).existsById(1L);
        verify(movieRepository, never()).deleteById(any());
    }


}

