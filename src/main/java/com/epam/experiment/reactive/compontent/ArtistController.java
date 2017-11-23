package com.epam.experiment.reactive.compontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epam.experiment.reactive.domain.Artist;

import reactor.core.publisher.Mono;

@RestController
public class ArtistController {

    private ArtistService service;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.service = artistService;
    }

    @RequestMapping("/artist/{id}")
    public Artist getArtist(
            @PathVariable String id,
            @RequestParam(defaultValue = "1000") int latency,
            @RequestParam(defaultValue = "500") int sleep,
            @RequestParam(defaultValue = "0") int prime) {
        return service.getArtist(id, latency, sleep, prime);
    }

    @RequestMapping("/artist/reactive/{id}")
    public Mono<Artist> getReactiveArtist(
            @PathVariable String id,
            @RequestParam(defaultValue = "1000") int latency,
            @RequestParam(defaultValue = "500") int sleep,
            @RequestParam(defaultValue = "0") int prime) {
        return service.getReactiveArtist(id, latency, sleep, prime);
    }
}
