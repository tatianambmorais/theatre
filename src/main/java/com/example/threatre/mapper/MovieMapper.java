package com.example.threatre.mapper;

import com.example.threatre.dto.MoviesAPIDTO;
import com.example.threatre.dto.MoviesDTO;
import com.example.threatre.model.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public Movie toEntity(MoviesAPIDTO dto) {
        Movie movie = new Movie();
        movie.setId(dto.id());
        movie.setTitle(dto.title());
        movie.setOverview(dto.overview());
        movie.setAdult(dto.adult());
        movie.setOriginalLanguage(dto.originalLanguage());
        movie.setBackdropPath(dto.backdropPath());
        movie.setPosterPath(dto.posterPath());
        movie.setReleaseDate(dto.releaseDate());
        movie.setPopularity(dto.popularity());
        movie.setVoteAverage(dto.voteAverage());
        movie.setVoteCount(dto.voteCount());
        movie.setVideo(dto.video());
        movie.setOriginalTitle(dto.originalTitle());
        movie.setGenreIds(dto.genreIds());

        return movie;
    }

    public MoviesDTO toDto(Movie movie) {
        return new MoviesDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getOverview()
        );
    }
}
