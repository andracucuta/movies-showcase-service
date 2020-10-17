package com.cucutaa.moviesshowcaseservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebClientConfig {

    @Bean
    public WebClient webClint(WebClient.Builder builder) {
        return builder
                .filter(logRequest())
                .filter(logResponseStatus())
                .build();
    }

    private ExchangeFilterFunction logResponseStatus() {
        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
            log.debug("Response status: {} {} {}", clientResponse.statusCode(),
                    clientResponse.headers().asHttpHeaders(),
                    clientResponse.headers().contentType());

            return Mono.just(clientResponse);
        });
    }

    private static ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());

            return next.exchange(clientRequest);
        };
    }
}
