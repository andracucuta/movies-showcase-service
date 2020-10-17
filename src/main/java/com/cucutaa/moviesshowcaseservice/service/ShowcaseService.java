package com.cucutaa.moviesshowcaseservice.service;

import com.cucutaa.moviesshowcaseservice.exceptions.MovieNotFoundException;
import com.cucutaa.moviesshowcaseservice.model.Movie;
import com.cucutaa.moviesshowcaseservice.model.ShowcaseQuery;
import com.cucutaa.moviesshowcaseservice.repository.ShowcaseRepository;
import com.fasterxml.jackson.databind.util.ArrayBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShowcaseService {

    private final ShowcaseRepository showcaseRepository;

    public Flux<Movie> getAllShowcases() {
        return Flux.fromIterable(showcaseRepository.findAll());
    }

    public Flux<Movie> getShowcase(ShowcaseQuery showcaseQuery) {

        Iterable<Movie> result = showcaseRepository.findByRatingAndYearAndGenresAndDirectorsName( showcaseQuery.getRating(),
                showcaseQuery.getYear(),
                showcaseQuery.getGenre(),
                showcaseQuery.getDirector().getName()
        );

        return Flux.fromIterable(result)
                .switchIfEmpty(Mono.error(new MovieNotFoundException(
                                String.format("Movie not found for query %s", showcaseQuery))));
    }
}
