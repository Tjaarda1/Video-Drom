package com.videodrom.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.videodrom.backend.entity.Movie;
import com.videodrom.backend.service.MovieService;
import com.videodrom.backend.service.ResourceService;

@RestController
@RequestMapping("/movies")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping()
    public ResponseEntity<List<Movie>> getAllMovies() {
        return new ResponseEntity<>(movieService.getAll(), HttpStatus.OK);
    }
    // @GetMapping("/{title}")
    // public ResponseEntity<Movie> getMovieByTitle(@PathVariable("title") String title) {
    //     return new ResponseEntity<>(movieService.getByTitle(title).get(0), HttpStatus.OK);
    // }
    @PostMapping()
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        return new ResponseEntity<>(movieService.saveMovie(movie), HttpStatus.CREATED);
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void handlePostRequest(@RequestPart(value="mp4File") MultipartFile mp4File,
                                  @RequestPart(value= "jpgFile") MultipartFile jpgFile,
                                  @RequestPart Movie movie
                                 ) {
// user here is a prepopulated User instance
        //Movie movie = mapper.mapToMovie(movieDto);

        Movie savedMovie = movieService.saveMovie(movie);
        resourceService.sendMovieToGoServer(mp4File, savedMovie.getUniqueKey());
        resourceService.saveJPGThumbnail(jpgFile, savedMovie.getUniqueKey());
    }
    @PutMapping()
    public ResponseEntity<Movie> updateMovie(@RequestBody Movie movie) {
        if (movieService.existById(movie.getId())) {
            return new ResponseEntity<>(movieService.saveMovie(movie), HttpStatus.ACCEPTED);
        }
        throw new IllegalArgumentException("Movie with id " + movie.getId() + " not found");
    }
    @DeleteMapping("/{movieId}")
    public ResponseEntity<HttpStatus> deleteMovie(@PathVariable("movieId") String movieId) {
        movieService.deleteMovie(Long.parseLong(movieId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // @GetMapping("/test")
    // public void runJob() {
    //     coreV1Api.toString();
        
    // }
}