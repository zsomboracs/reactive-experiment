package com.epam.experiment.reactive.compontent;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.epam.experiment.reactive.domain.Artist;

import reactor.core.publisher.Mono;

@Component
public class ArtistRepository {

    private static final String SERVICE_URL = "http://localhost:8090/artist/{id}?latency={latency}";

    private RestTemplate restTemplate;
    private WebClient webClient;

    @Autowired
    public ArtistRepository(RestTemplate restTemplate, WebClient webClient) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
    }

    public Artist getArtist(String id, int latency) {
        return restTemplate.getForObject(SERVICE_URL, Artist.class, getParams(id, latency));
    }

    public Mono<Artist> getReactiveArtist(String id, int latency) {
        return webClient.get()
                .uri(SERVICE_URL, getParams(id, latency))
                .retrieve()
                .bodyToMono(Artist.class);
    }

    private Map<String, Object> getParams(String id, int latency) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("latency", latency);
        return params;
    }

}
