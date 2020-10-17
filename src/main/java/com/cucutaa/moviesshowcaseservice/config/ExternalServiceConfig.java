package com.cucutaa.moviesshowcaseservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "external-services")
public class ExternalServiceConfig {

    private final MgTechTestConfig mgTechTest = new MgTechTestConfig();

    @Getter
    @Setter
    public static class MgTechTestConfig {
        private String basePath;
        private String getShowcasePath;
    }
}
