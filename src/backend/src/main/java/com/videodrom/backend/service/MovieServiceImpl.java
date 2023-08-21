package com.videodrom.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.videodrom.backend.entity.Movie;
import com.videodrom.backend.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository movieRepo;
   

    @Override
    public List<Movie> getByTitle(String title) {
        return movieRepo.findByTitle(title);
    }

    @Override
    public boolean existById(Long movieId) {
        return movieRepo.existsById(movieId);
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepo.deleteById(movieId);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> getAll() {
        return movieRepo.findAll();
    }

    
}
