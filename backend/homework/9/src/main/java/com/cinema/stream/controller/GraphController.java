package com.cinema.stream.controller;

import com.cinema.stream.dto.AddRatings;
import com.cinema.stream.entity.Director;
import com.cinema.stream.entity.Movie;
import com.cinema.stream.service.DirectorService;
import com.cinema.stream.service.MovieService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class GraphController {

    private final MovieService movieService;
    private final DirectorService directorService;

    public GraphController(MovieService movieService, DirectorService directorService){
        this.movieService = movieService;
        this.directorService = directorService;
    }

    @QueryMapping
    public Movie findMovieById(@Argument Long id){
        return movieService.findMovieById(id);
    }

    @QueryMapping
    public List<Movie> getMovies(){
        return movieService.getAllMovies();
    }

    @MutationMapping
    public Movie addReview(@Argument String id, @Argument AddRatings input){

        return movieService.addRatingsForMovie(id, input.getComment(), input.getRating());

    }

    @SchemaMapping
    public Director director(Movie movie) {
        return directorService.getDirector(movie.getDirectorId());
    }


}
