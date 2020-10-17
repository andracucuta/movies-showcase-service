package com.cucutaa.moviesshowcaseservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RedisHash("movie")
public class Movie implements Serializable {
    @Indexed
    private String id;
    private String body;
    private List<CardImage> cardImages;
    private List<Cast> cast;
    private String cert;
    @JsonProperty("class")
    private Type type;
    private List<Director> directors;
    private long duration;
    @Indexed
    private List<String> genres;
    private String headline;
    private List<KeyArtImage> keyArtImages;
    private String lastUpdated;
    private String quote;
    @Indexed
    private String rating;
    private String reviewAuthor;
    private String skyGoId;
    private String skyGoUrl;
    private String sum;
    private String synopsis;
    private String url;
    private List<Video> videos;
    private ViewingWindow viewingWindow;
    @Indexed
    private String year;
    private List<Galleries> galleries;
    private String sgid;
    private String sgUrl;
}
