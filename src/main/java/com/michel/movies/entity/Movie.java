package com.michel.movies.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "mv_movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;

    private String title;

    @Column(name = "movie_year")
    private int year;
    private String studios;
    private String producers;
    private Boolean winner;

}