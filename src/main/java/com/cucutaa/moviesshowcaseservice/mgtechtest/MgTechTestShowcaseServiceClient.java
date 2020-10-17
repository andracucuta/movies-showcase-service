package com.cucutaa.moviesshowcaseservice.mgtechtest;

import com.cucutaa.moviesshowcaseservice.config.ExternalServiceConfig;
import com.cucutaa.moviesshowcaseservice.model.Movie;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.HttpHeaders.ACCEPT;

@Service
@Slf4j
@RequiredArgsConstructor
public class MgTechTestShowcaseServiceClient {

    private final WebClient webClient;
    private final ExternalServiceConfig config;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Mono<List<Movie>> getShowcase() {
        Mono<String> response =  webClient
                .get()
                .uri(buildUri())
                .header(ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(String.class);

        return response.map(MgTechTestShowcaseServiceClient::encoding)
                .map(MgTechTestShowcaseServiceClient::changeStringToObject);
    }


    public static String encoding(String value) {

        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private static List<Movie> changeStringToObject(String value) {

        return objectMapper.readValue(value, new TypeReference<List<Movie>>(){});
    }
    private String buildUri() {
        return UriComponentsBuilder
                .fromUriString(config.getMgTechTest().getGetShowcasePath())
                .build()
                .toUriString();
    }
}
