package com.cucutaa.moviesshowcaseservice.rest;

import com.cucutaa.moviesshowcaseservice.model.Director;
import com.cucutaa.moviesshowcaseservice.model.Movie;
import com.cucutaa.moviesshowcaseservice.model.ShowcaseQuery;
import com.cucutaa.moviesshowcaseservice.service.ShowcaseService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/showcase")
@RequiredArgsConstructor
public class ShowcaseController {

    private final ShowcaseService showcaseService;

    @Operation(
            summary = "Gets the whole list of showcase"
    )
    @GetMapping(path="/all",  produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Flux<Movie> getAllShowcases() {
        return showcaseService.getAllShowcases();
    }

    @Operation(
            summary = "Gets the list of showcase",
            description = "Gets the list of showcase that can be filtered by genres, directors, years or ratings."
    )
    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Flux<Movie> getShowcase(@RequestParam(required = false) String genre,
                                   @RequestParam(required = false) String directorName,
                                   @RequestParam(required = false) String rating,
                                   @RequestParam(required = false) String year) {

        var showcaseQuery = ShowcaseQuery
                .builder()
                .director(new Director(directorName))
                .genre(genre)
                .rating(rating)
                .year(year)
                .build();

        return showcaseService.getShowcase(showcaseQuery);
    }
}
