package com.michel.movies.config;

import com.michel.movies.MoviesApplication;
import com.michel.movies.entity.Movie;
import com.michel.movies.repo.MovieRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Slf4j
@Component
public class ServerInitializer implements CommandLineRunner {

    @Autowired
    private MovieRepo movieRepo;

    @Override
    public void run(String... args) throws Exception {
        for (String arg : args) {
            File file = new File(arg);
            int lineNumber = 0;
            int errors = 0;
            if (file.exists()) {
                log.info("[IMPORTING] Processing file: " + file.getPath());
                try (FileReader reader = new FileReader(file)) {
                    try (BufferedReader br = new BufferedReader(reader)) {
                        for (String line = br.readLine(); line != null; line = br.readLine()) {
                            lineNumber++;
                            if (lineNumber > 1) {
                                String sanityCheckResult = sanityCheck(lineNumber, line);
                                if (sanityCheckResult != null) {
                                    errors++;
                                    log.error("[IMPORTING] " + sanityCheckResult);
                                } else {
                                    String[] fields = line.split(";");
                                    Movie movie = new Movie();
                                    movie.setYear(Integer.parseInt(fields[0]));
                                    movie.setTitle(fields[1]);
                                    movie.setStudios(fields[2]);
                                    movie.setProducers(fields[3]);
                                    movie.setWinner(fields.length >= 5 && fields[4].equalsIgnoreCase("yes"));
                                    movieRepo.save(movie);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public String sanityCheck(int lineNumber, String line) {
        String[] fields = line.split(";");
        if (fields.length < 4) {
            return String.format("Line number %s have minus than 4 fields!", lineNumber);
        }
        try {
            Integer.parseInt(fields[0]);
        } catch (Exception ex) {
            return String.format("Line number %s have an invalid \"year\" field value!", lineNumber);
        }
        return null;
    }
}