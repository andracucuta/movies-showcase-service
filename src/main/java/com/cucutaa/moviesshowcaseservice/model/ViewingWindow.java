package com.cucutaa.moviesshowcaseservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Data
@NoArgsConstructor
@RedisHash("viewingwindow")
public class ViewingWindow implements Serializable {

    private String startDate;
    private String wayToWatch;
    private String endDate;
    private String title;
}
