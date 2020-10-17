package com.cucutaa.moviesshowcaseservice.showcaseRepository;

import com.cucutaa.moviesshowcaseservice.mgtechtest.MgTechTestShowcaseServiceClient;
import com.cucutaa.moviesshowcaseservice.model.Movie;
import com.cucutaa.moviesshowcaseservice.repository.ShowcaseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ShowcaseLoader {

    private final MgTechTestShowcaseServiceClient mgTechTestShowcaseServiceClient;
    private final ShowcaseRepository showcaseRepository;

    @EventListener
    public void loadData(ApplicationReadyEvent applicationReadyEvent) {

        List<Movie> movies = Objects.requireNonNull(mgTechTestShowcaseServiceClient.getShowcase()
                .map(Collection::parallelStream)
                .block())
                .collect(Collectors.toList());

        showcaseRepository.saveAll(movies);
    }
}
