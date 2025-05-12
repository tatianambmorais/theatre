package com.example.threatre.mapper;

import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.model.Movie;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovieMapper {
   public Movie toEntity(MoviesDTO dto) {
       Movie movie = new Movie();
       movie.setId(dto.id());
       movie.setTitle(dto.title());
       movie.setOverview(dto.overview());
       movie.setAdult(dto.adult());
       movie.setOriginalLanguage(dto.originalLanguage());
       return movie;
   }

    public MoviesDTO toDto(Movie movie) {
        return new MoviesDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getOriginalLanguage(),
                movie.getOverview(),
                movie.isAdult()
        );
    }
}
