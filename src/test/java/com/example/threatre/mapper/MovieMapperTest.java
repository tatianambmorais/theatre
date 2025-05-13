package com.example.threatre.mapper;


import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieMapperTest {

    private MovieMapper movieMapper;

    @BeforeEach
    void setUp() {
        movieMapper = new MovieMapper();
    }

    @Test
    void testToEntity() {
        // Arrange
        MoviesAPIDTO dto = new MoviesAPIDTO(
                1L,
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

        Movie movie = movieMapper.toEntity(dto);

        assertNotNull(movie);
        assertEquals(dto.id(), movie.getId());
        assertEquals(dto.title(), movie.getTitle());
        assertEquals(dto.overview(), movie.getOverview());
        assertEquals(dto.adult(), movie.getAdult());
        assertEquals(dto.originalLanguage(), movie.getOriginalLanguage());
        assertEquals(dto.backdropPath(), movie.getBackdropPath());
        assertEquals(dto.posterPath(), movie.getPosterPath());
        assertEquals(dto.releaseDate(), movie.getReleaseDate());
        assertEquals(dto.popularity(), movie.getPopularity());
        assertEquals(dto.voteAverage(), movie.getVoteAverage());
        assertEquals(dto.voteCount(), movie.getVoteCount());
        assertEquals(dto.video(), movie.getVideo());
        assertEquals(dto.originalTitle(), movie.getOriginalTitle());
        assertEquals(dto.genreIds(), movie.getGenreIds());
    }

    @Test
    void testToDto() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Avatar");
        movie.setOverview("Um filme de ficção");

        MoviesDTO dto = movieMapper.toDto(movie);

        assertNotNull(dto);
        assertEquals(movie.getId(), dto.id());
        assertEquals(movie.getTitle(), dto.title());
        assertEquals(movie.getOverview(), dto.overview());
    }
}
