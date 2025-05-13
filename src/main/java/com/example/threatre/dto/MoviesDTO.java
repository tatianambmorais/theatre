package com.example.threatre.dto;

import lombok.Getter;
import java.util.List;

public record MoviesDTO(
        Long id,
        String title,
        String overview
) {}
