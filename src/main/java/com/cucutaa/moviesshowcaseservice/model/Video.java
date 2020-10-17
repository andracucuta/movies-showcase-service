package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@RedisHash("video")
public class Video implements Serializable {
    private String title;
    private List<Alternative> alternatives;
    private String type;
    private String url;
    private String thumbnailUrl;
}
