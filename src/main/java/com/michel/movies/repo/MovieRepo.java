package com.michel.movies.repo;

import com.michel.movies.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {

    @Query("select x.producers from Movie x where x.winner = true group by x.producers")
    List<String> findAllGroupByProducers();

    @Query("select x from Movie x where x.winner = true and x.producers = :producers order by x.year")
    List<Movie> findByProducers(String producers);
}
