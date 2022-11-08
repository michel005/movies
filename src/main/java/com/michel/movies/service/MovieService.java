package com.michel.movies.service;

import com.michel.movies.entity.Movie;
import com.michel.movies.model.ProducerModel;
import com.michel.movies.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    private List<ProducerModel> initialData() {

        List<String> producers = movieRepo.findAllGroupByProducers();
        List<ProducerModel> models = new ArrayList<>();

        ProducerModel[] current = new ProducerModel[1];

        producers.forEach((producer) -> {
            current[0] = new ProducerModel();
            current[0].setProducers(producer);

            int[] sizeBefore = new int[] {models.size()};
            movieRepo.findByProducers(producer).forEach((movie) -> {
                if (current[0].getPreviousWin() == null) {
                    current[0].setPreviousWin(movie.getYear());
                } else
                if (current[0].getFollowingWin() == null) {
                    current[0].setFollowingWin(movie.getYear());
                    current[0].setInterval(current[0].getFollowingWin() - current[0].getPreviousWin());
                    models.add(current[0]);
                    current[0] = new ProducerModel();
                    current[0].setProducers(producer);
                    current[0].setPreviousWin(movie.getYear());
                }
            });
        });
        return models.stream().sorted(Comparator.comparing(ProducerModel::getInterval)).collect(Collectors.toList());
    }

    public List<ProducerModel> findWorstProducer() {
        List<ProducerModel> temporary = initialData();
        List<ProducerModel> worst = new ArrayList<>();
        Integer[] worstInterval = new Integer[] {0};
        temporary.forEach((model) -> {
            if (worstInterval[0] < model.getInterval()) {
                worstInterval[0] = model.getInterval();
            }
        });
        return temporary.stream().filter((model) -> Objects.equals(model.getInterval(), worstInterval[0])).collect(Collectors.toList());
    }

    public List<ProducerModel> findBestProducer() {
        List<ProducerModel> temporary = initialData();
        List<ProducerModel> worst = new ArrayList<>();
        return temporary.stream().filter((model) -> Objects.equals(model.getInterval(), temporary.get(0).getInterval())).collect(Collectors.toList());
    }

}