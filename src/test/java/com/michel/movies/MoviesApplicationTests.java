package com.michel.movies;

import com.michel.movies.repo.MovieRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MoviesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepo movieRepo;

    private SpringApplicationBuilder subject = null;

    @BeforeEach
    public void before() {
        subject = new SpringApplicationBuilder(MoviesApplication.class);
    }

    @AfterEach
    public void after() {
        movieRepo.deleteAll();
        subject.context().close();
    }

    @Test
    public void oficialMovieList() throws Exception {
        subject.run("movieList.csv");
        this.mockMvc
                .perform(get("/movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"max\":[{\"producers\":\"MATTHEW VAUGHN\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}],\"min\":[{\"producers\":\"JOEL SILVER\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}]}"));
    }

    @Test
    public void maxIntervalWith10AndMinIntervalWith1() throws Exception {
        subject.run("exemplo1.csv");
        this.mockMvc
                .perform(get("/movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"max\":[{\"producers\":\"Producer 001\",\"interval\":10,\"previousWin\":2000,\"followingWin\":2010}],\"min\":[{\"producers\":\"Producer 002\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}]}"));
    }

    @Test
    public void maxIntervalWith22AndMinIntervalWith1() throws Exception {
        subject.run("exemplo2.csv");
        this.mockMvc
                .perform(get("/movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"max\":[{\"producers\":\"Producer 003\",\"interval\":22,\"previousWin\":2000,\"followingWin\":2022}],\"min\":[{\"producers\":\"Producer 002\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}]}"));
    }

    @Test
    public void emptyBecauseHaveOnlyMoviesNotWinners() throws Exception {
        subject.run("exemplo3.csv");
        this.mockMvc
                .perform(get("/movie")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"max\":[],\"min\":[]}"));
    }

}