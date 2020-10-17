package com.cucutaa.moviesshowcaseservice.service;

import com.cucutaa.moviesshowcaseservice.model.Director;
import com.cucutaa.moviesshowcaseservice.model.Movie;
import com.cucutaa.moviesshowcaseservice.model.ShowcaseQuery;
import com.cucutaa.moviesshowcaseservice.repository.ShowcaseRepository;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ShowcaseServiceTest {

    @InjectMocks
    private ShowcaseService showcaseService;

    @Mock
    private ShowcaseRepository showcaseRepository;

    @Test
    public void getAllShowcases_ReturnSuccessfully() {
        val savedMovie = buildMovie();
        when(showcaseRepository.findAll()).thenReturn(List.of(savedMovie));

        StepVerifier.create(showcaseService.getAllShowcases())
                .assertNext(result -> {
                    assertEquals(savedMovie.getYear(), result.getYear());
                    assertEquals(savedMovie.getRating(), result.getRating());
                }).verifyComplete();
    }

    @Test
    public void getShowcase_ReturnSuccessfully() {
        val savedMovies = buildMovieList();
        val showcaseQuery = new ShowcaseQuery();
        showcaseQuery.setYear("2001");
        showcaseQuery.setDirector(new Director("Thor"));
        showcaseQuery.setGenre("Comedy");
        showcaseQuery.setRating("5");

        when(showcaseRepository.findByRatingAndYearAndGenresAndDirectorsName(
                showcaseQuery.getRating(),
                showcaseQuery.getYear(),
                showcaseQuery.getGenre(),
                showcaseQuery.getDirector().getName()
        )).
                thenReturn(savedMovies);

        StepVerifier.create(showcaseService.getShowcase(showcaseQuery))
                .assertNext(result -> {
                   assertEquals("2001", result.getYear());
                   assertEquals("Thor", result.getDirectors().get(0).getName());
                   assertEquals("5", result.getRating());
                   assertEquals("Comedy", result.getGenres().get(0));
                }).verifyComplete();
    }

    @Test
    public void getShowcase_FilteredByDirectorAndYear() {
        val savedMovies = buildMovieList();
        val showcaseQuery = new ShowcaseQuery();
        showcaseQuery.setYear("2001");
        showcaseQuery.setDirector(new Director("Batman"));
        showcaseQuery.setGenre(null);
        showcaseQuery.setRating(null);

        when(showcaseRepository.findByRatingAndYearAndGenresAndDirectorsName(
                showcaseQuery.getRating(),
                showcaseQuery.getYear(),
                showcaseQuery.getGenre(),
                showcaseQuery.getDirector().getName()
        )).
                thenReturn(savedMovies.subList(1, 1));

        StepVerifier.create(showcaseService.getShowcase(showcaseQuery))
                .thenConsumeWhile(result -> {
                    assertEquals("2001", result.getYear());
                    assertEquals("Batman", result.getDirectors().get(0).getName());
                    assertNull(result.getRating());
                    assertNull(result.getGenres().get(0));
                    return true;
                })
                .verifyComplete();
    }

    private Movie buildMovie(){
        Movie movie = new Movie();
        movie.setYear("2000");
        movie.setRating("10");

        return movie;
    }

    private List<Movie> buildMovieList(){
        List<Movie> movies = new ArrayList<>();

        Movie movie2000 = new Movie();
        movie2000.setYear("2000");
        movie2000.setDirectors(Arrays.asList(new Director("Batman")));

        Movie movie2001 = new Movie();
        movie2001.setYear("2001");
        movie2001.setRating("5");
        movie2001.setDirectors(Arrays.asList(new Director("Thor")));
        movie2001.setGenres(Arrays.asList("Comedy"));
        movies.add(movie2001);

        return movies;
    }
}
