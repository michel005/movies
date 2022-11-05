package com.michel.movies.api;

import com.michel.movies.model.ProducerModel;
import com.michel.movies.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/movie")
public class MovieAPI {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        Map<String, List<ProducerModel>> response = new TreeMap<>();
        response.put("max", movieService.findWorstProducer());
        response.put("min", movieService.findBestProducer());
        return ResponseEntity.ok(response);
    }

}
