package com.michel.movies.model;

import lombok.Data;

@Data
public class ProducerModel {

    private String producers;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;

}
