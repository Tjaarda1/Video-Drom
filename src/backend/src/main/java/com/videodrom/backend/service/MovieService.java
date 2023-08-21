package com.videodrom.backend.service;

import java.util.List;

import com.videodrom.backend.entity.Movie;

public interface MovieService {
    
    public List<Movie> getByTitle(String title);

    public boolean existById(Long movieId);

    public void deleteMovie(Long movieId);
    
    public Movie saveMovie(Movie movie);

    public List<Movie> getAll();
}
