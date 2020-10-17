package com.cucutaa.moviesshowcaseservice.externalservices;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.mockserver.client.MockServerClient;
import org.mockserver.matchers.Times;
import org.springframework.core.io.ClassPathResource;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@RequiredArgsConstructor
public class MgTechMock {

    private final MockServerClient mockServerClient;

    @SneakyThrows
    public void mockGetShowCase() {
        val doc = new ClassPathResource("/showcases/showcase.json").getInputStream().readAllBytes();
        mockServerClient
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/showcase.json"),
                        Times.exactly(1))
                .respond(
                        response().
                                withStatusCode(200)
                                .withBody(doc)
                );
    }
}
