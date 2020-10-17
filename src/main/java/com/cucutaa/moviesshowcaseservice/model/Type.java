package com.cucutaa.moviesshowcaseservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("type")
public enum Type implements Serializable {
    @JsonProperty("Movie")
    MOVIE("Movie"),
    @JsonProperty("Tv_show")
    TV_SHOW("TvShow");

    private final String value;

    Type(String value) {
        this.value = value;
    }
}
