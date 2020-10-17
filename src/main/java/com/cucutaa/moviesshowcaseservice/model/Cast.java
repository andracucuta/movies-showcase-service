package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RedisHash("cast")
public class Cast implements Serializable {

    private String name;
}
