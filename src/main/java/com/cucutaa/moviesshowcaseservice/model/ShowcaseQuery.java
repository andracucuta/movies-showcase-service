package com.cucutaa.moviesshowcaseservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ShowcaseQuery {
     String genre;
     Director director;
     String rating ;
     String year;
}
