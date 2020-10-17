package com.cucutaa.moviesshowcaseservice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.ClassPathResource;

@UtilityClass
public class TestHelper {

    @SneakyThrows
    public static JsonNode readResourceFileAsJson(String resourceFileName){
        return new ObjectMapper().readTree(new ClassPathResource(resourceFileName).getInputStream());
    }
}
