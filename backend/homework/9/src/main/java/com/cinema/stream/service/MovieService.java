package com.cinema.stream.service;

import com.cinema.stream.dto.AddRatings;
import com.cinema.stream.entity.Movie;
import com.cinema.stream.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public  MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public Movie findMovieById(Long id){
        return movieRepository.getReferenceById(id);
    }

    public Movie addRatingsForMovie(String id, String comment, int rating){

        Movie movie = movieRepository.getReferenceById(Long.parseLong(id));
        movie.setComment(comment);
        movie.setRating(rating);
        return movie;


    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAll();
    }

}
