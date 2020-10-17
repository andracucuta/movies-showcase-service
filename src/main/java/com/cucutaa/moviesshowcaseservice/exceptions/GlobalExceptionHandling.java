package com.cucutaa.moviesshowcaseservice.exceptions;

import error.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Mono<ApiError>> handlerException(Exception ex){
        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown exception");

        return logAndBuildResponse(apiError, ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Mono<ApiError>> handlerIllegalArgumentException(IllegalArgumentException ex){
        var apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Illegal argument");

        return logAndBuildResponse(apiError, ex);
    }

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Mono<ApiError>> handlerMovieNotFoundException(MovieNotFoundException ex){
        var apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());

        return logAndBuildResponse(apiError, ex);
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<Mono<ApiError>> handleHttpStatusCodeException(HttpStatusCodeException e) {
        ApiError apiError = new ApiError(HttpStatus.FAILED_DEPENDENCY,
                String.format("Failure when calling external dependency, got a %s status code from it", e.getStatusCode()));
        return logAndBuildResponse(apiError, e);
    }

    private ResponseEntity<Mono<ApiError>> logAndBuildResponse(ApiError apiError, Exception e) {
        if (apiError.getStatus().is5xxServerError()) {
            log.error("Exception handler mapped exception", e);
        } else {
            log.info("Exception handler mapped exception", e);
        }
        return ResponseEntity
                .status(apiError.getStatus())
                .body(Mono.just(apiError));
    }
}
