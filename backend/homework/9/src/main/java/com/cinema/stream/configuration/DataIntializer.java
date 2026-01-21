package com.cinema.stream.configuration;

import com.cinema.stream.entity.Director;
import com.cinema.stream.entity.Movie;
import com.cinema.stream.repository.DirectorRepository;
import com.cinema.stream.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataIntializer {

    @Bean
    CommandLineRunner initData(
            MovieRepository movieRepository,
            DirectorRepository directorRepository
    ){
        return args -> {

            Director d1 = new Director();
            d1.setName("Christopher Nolan");
            d1.setTotalAwards(34);

            Director d2 = new Director();
            d2.setName("Steven Spielberg");
            d2.setTotalAwards(58);

            directorRepository.saveAll(List.of(d1, d2));

            Movie m1 = new Movie();
            m1.setTitle("Inception");
            m1.setGenre("Sci-Fi");
            m1.setDirectorId(d1.getId());

            Movie m2 = new Movie();
            m2.setTitle("Interstellar");
            m2.setGenre("Sci-Fi");
            m2.setDirectorId(d1.getId());

            Movie m3 = new Movie();
            m3.setTitle("Jurassic Park");
            m3.setGenre("Adventure");
            m3.setDirectorId(d2.getId());

            movieRepository.saveAll(List.of(m1, m2, m3));
        };


    }
}
