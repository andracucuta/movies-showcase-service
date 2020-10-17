package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RedisHash("galleries")
public class Galleries implements Serializable {

    private String id;
    private String title;
    private String url;
    private String thumbnailUrl;
}
