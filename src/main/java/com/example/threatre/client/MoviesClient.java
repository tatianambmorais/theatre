package com.example.threatre.client;

import com.example.threatre.config.FeignClientConfig;
import com.example.threatre.dto.MoviesResponseWrapperDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "moviesClient", url = "https://api.themoviedb.org/3")
public interface MoviesClient {
    @GetMapping("/search/movie")
    MoviesResponseWrapperDTO searchMovie(@RequestParam("query") String title, @RequestHeader("Authorization") String token);
}
