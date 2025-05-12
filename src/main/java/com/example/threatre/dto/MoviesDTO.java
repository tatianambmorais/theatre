package com.example.threatre.dto;


import lombok.Getter;

import java.util.List;
public record MoviesDTO(
        Long id,
        String title,
        String originalLanguage,
        String overview,
//        List<Genre> genreIds,
        boolean adult
) {}