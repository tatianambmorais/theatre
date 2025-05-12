package com.example.threatre.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "original_language")
    private String originalLanguage;

    @Column(name = "overview", columnDefinition = "TEXT")
    private String overview;

    @Column(name = "adult")
    private boolean adult;
}
