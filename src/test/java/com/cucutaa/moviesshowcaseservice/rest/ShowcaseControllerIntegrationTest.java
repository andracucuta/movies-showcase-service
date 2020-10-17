package com.cucutaa.moviesshowcaseservice.rest;

import com.cucutaa.moviesshowcaseservice.ContainersInitializer;
import com.cucutaa.moviesshowcaseservice.repository.ShowcaseRepository;
import com.cucutaa.moviesshowcaseservice.service.ShowcaseService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@Testcontainers
@ContextConfiguration(initializers = {ContainersInitializer.class})
@RunWith(SpringRunner.class)
public class ShowcaseControllerIntegrationTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ShowcaseService showcaseService;

    @Autowired
    private ShowcaseRepository showcaseRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Before
    public void setUp(){
        this.webTestClient = WebTestClient
                .bindToApplicationContext(this.context)
                .build();
    }

    @Test
    public void getAllShowcasesSuccessfully() {

        webTestClient.get()
                .uri("/showcase")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }


    @Test
    public void getShowcase_ShouldReturn_Expected_Result() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.findAll()))
                .expectNextCount(2)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?year=2012")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void getShowcase_FilterByAllParams() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.
                        findByRatingAndYearAndGenresAndDirectorsName("5", "2012", "Comedy", "Andy Fickman")))
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?year=2012&genre=Comedy&rating=5&directorName=Andy Fickman")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void getShowcase_FilterByYear() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.
                        findByRatingAndYearAndGenresAndDirectorsName(null, "2012", null, null)))
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?year=2012")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void getShowcase_ShouldReturnStatusNotFound() {
        webTestClient.get()
                .uri("/showcase?year=20122")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void getShowcase_FilterByGenre() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.
                        findByRatingAndYearAndGenresAndDirectorsName(null, null, "Family", null)))
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?genre=Family")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void getShowcase_FilterByRating() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.
                        findByRatingAndYearAndGenresAndDirectorsName("5", null, null, null)))
                .expectNextCount(2)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?rating=5")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }

    @Test
    public void getShowcase_FilterByDirector() {

        StepVerifier
                .create(Flux.fromIterable(showcaseRepository.
                        findByRatingAndYearAndGenresAndDirectorsName(null, null, null, "Andy Fickman")))
                .expectNextCount(1)
                .verifyComplete();

        webTestClient.get()
                .uri("/showcase?director=Andy Fickman")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.OK)
                .expectBody().jsonPath("$").isNotEmpty();
    }
}
