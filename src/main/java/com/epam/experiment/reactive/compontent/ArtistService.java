package com.epam.experiment.reactive.compontent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.experiment.reactive.domain.Artist;

import reactor.core.publisher.Mono;

@Component
public class ArtistService {

    private ArtistRepository repository;

    @Autowired
    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    public Artist getArtist(String id, int latency, int sleep, int prime) {
        Artist artist = repository.getArtist(id, latency);

        sleep(sleep);
        nthPrime(prime);

        return artist;
    }

    public Mono<Artist> getReactiveArtist(String id, int latency, int sleep, int prime) {
        return repository.getReactiveArtist(id, latency)
                .map(artist -> {
                    sleep(sleep);
                    nthPrime(prime);
                    return artist;
                });
    }

    public void sleep(int sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int nthPrime(int n) {
        int candidate, count;
        for (candidate = 2, count = 0; count < n; ++candidate) {
            if (isPrime(candidate)) {
                ++count;
            }
        }
        return candidate - 1;
    }

    private boolean isPrime(int n) {
        for (int i = 2; i < n; ++i) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }


}
