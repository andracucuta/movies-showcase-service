package com.cucutaa.moviesshowcaseservice.repository;

import com.cucutaa.moviesshowcaseservice.model.Movie;
import org.kolobok.annotation.FindWithOptionalParams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowcaseRepository extends JpaRepository<Movie, String> {

    @FindWithOptionalParams
    Iterable<Movie> findByRatingAndYearAndGenresAndDirectorsName(String rating, String year, String genre, String name);
}