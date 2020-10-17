package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RedisHash("keyartimage")
public class KeyArtImage implements Serializable {

    private String url;
    private long h;
    private long w;
}
