package com.example.threatre.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

public record MoviesAPIDTO(
        Long id,
        String title,
        @JsonProperty("original_title")
        String originalTitle,
        @JsonProperty("original_language")
        String originalLanguage,
        String overview,
        @JsonProperty("poster_path")
        String posterPath,
        @JsonProperty("backdrop_path")
        String backdropPath,
        @JsonProperty("release_date")
        String releaseDate,
        Double popularity,
        @JsonProperty("vote_average")
        Double voteAverage,
        @JsonProperty("vote_count")
        Integer voteCount,
        Boolean adult,
        Boolean video,
        @JsonProperty("genre_ids")
        List<Integer> genreIds
) {}
