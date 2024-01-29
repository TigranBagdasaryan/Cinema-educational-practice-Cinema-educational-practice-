package com.cinema.movie.controllers;

import com.cinema.movie.exception.MovieAlreadyExistsException;
import com.cinema.movie.exception.NotFoundException;
import com.cinema.movie.models.MovieList;
import com.cinema.movie.models.Movies;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("cinema")
public class Controllers {
    private final MovieList movieList = new MovieList();

    @GetMapping
    public List<Movies> list() throws  FileNotFoundException {
        movieList.readYaml();
        return movieList.getCinemaList();
    }

    @GetMapping("{id}")
    public Movies getOne(@PathVariable String id) throws IOException {
        movieList.readYaml();
        Movies movie = movieList.getCinemaListById(id);
        if (movie == null) {
            throw new NotFoundException(id); // Генерируем NotFoundException, если фильм не найден
        }
        return movie;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Movies create(@RequestBody Movies model) throws IOException {
        movieList.readYaml();
        String id = model.getId();

        // Проверяем, существует ли фильм с таким же id
        Movies existingMovie = movieList.getCinemaListById(id);
        if (existingMovie != null) {
            throw new MovieAlreadyExistsException(id); // Генерируем исключение, если фильм уже существует
        }


        double randomProbability = Math.random();
        model.setProbability(randomProbability);
        movieList.getCinemaList().add(model);
        movieList.saveYaml();
        return model;
    }

    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable String id) throws IOException {
        movieList.readYaml();
        Movies existingMovie = movieList.getCinemaListById(id);

        if (existingMovie == null) {
            throw new NotFoundException(id);
        }

        movieList.deleteMovie(id);
        movieList.saveYaml();
    }

    @PutMapping("{id}")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Movies update(@PathVariable String id, @RequestBody Movies model) throws IOException {
        movieList.readYaml(); // Указываем путь к файлу
        Movies movie = movieList.getCinemaListById(id);

        Movies existingMovie = movieList.getCinemaListById(id);
        /*  if (existingMovie != null) {
            throwgit new MovieAlreadyExistsException(id); // Генерируем исключение, если фильм уже существует
        }
*/
        // Проверяем, и устанавливаем только не-null значения

        if (model.getId() != null) {
            movie.setId(model.getId());
        }

        if (model.getStartTime() != null) {
            movie.setStartTime(model.getStartTime());
        }
        if (model.getMovieName() != null) {
            movie.setMovieName(model.getMovieName());
        }
        if (model.getDuration() != null) {
            movie.setDuration(model.getDuration());
        }
        if (model.getTicketPrice() != 0.0) {
            movie.setTicketPrice(model.getTicketPrice());
        }
        if (model.getProbability() != 0.0) {
            movie.setProbability(model.getProbability());
        }

        movieList.saveYaml();
        return movie;
    }
}
