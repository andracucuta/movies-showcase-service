package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RedisHash("alternative")
public class Alternative implements Serializable {

    private String quality;
    private String url;
}
