package com.cinema.movie.exception;

public class MovieAlreadyExistsException extends RuntimeException {
    public MovieAlreadyExistsException(String id) {
        super("Фильм с id " + id + " уже существует");
    }
}
