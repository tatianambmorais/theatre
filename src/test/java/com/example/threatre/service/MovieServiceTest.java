package com.example.threatre.service;

import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import com.example.threatre.exception.ResourceNotFoundException;
import com.example.threatre.mapper.MovieMapper;
import com.example.threatre.model.Movie;
import com.example.threatre.repository.MovieRepository;
import com.example.threatre.client.MoviesClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @Mock
    private MoviesClient moviesClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAndSaveMovie_success() {
        String title = "Avatar";
        MoviesResponseWrapperDTO responseWrapper = new MoviesResponseWrapperDTO(
                List.of(new MoviesAPIDTO(1L, title, title, "en", "Um filme de ficção",
                        "poster.jpg", "backdrop.jpg", "2009-12-18", 7.8, 8.5,
                        1500, false, false, List.of(28, 12)))
        );

        // Mockando o comportamento do client
        when(moviesClient.searchMovie(eq(title), anyString())).thenReturn(responseWrapper);
        when(movieMapper.toEntity(any(MoviesAPIDTO.class))).thenReturn(new Movie());
        when(movieRepository.save(any(Movie.class))).thenReturn(new Movie());

        // Executando o serviço
        MoviesAPIDTO result = movieService.getAndSaveMovie(title);

        // Verificando o resultado
        assertNotNull(result);
        assertEquals("Avatar", result.title());
        verify(moviesClient).searchMovie(eq(title), anyString());
        verify(movieRepository).save(any(Movie.class));
    }

    @Test
    void testGetAndSaveMovie_movieNotFound() {
        String title = "Inexistente";

        // Mockando o retorno de um título não encontrado
        when(moviesClient.searchMovie(eq(title), anyString())).thenReturn(new MoviesResponseWrapperDTO(List.of()));

        // Verificando se a exceção é lançada corretamente
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> movieService.getAndSaveMovie(title));
        assertEquals("Filme com título \"Inexistente\" não encontrado", exception.getMessage());
    }

    @Test
    void testGetAndSaveMovie_emptyTitle() {
        // Testando título vazio
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> movieService.getAndSaveMovie("  "));
        assertEquals("O título do filme não pode ser vazio", exception.getMessage());
    }

    @Test
    void testGetAndSaveMovie_nullTitle() {
        // Testando título nulo
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> movieService.getAndSaveMovie(null));
        assertEquals("O título do filme não pode ser vazio", exception.getMessage());
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
    void testDeleteMovie_success() {
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
