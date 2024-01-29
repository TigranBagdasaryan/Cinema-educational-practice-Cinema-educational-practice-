package com.cinema.movie.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String id) {
        super("Фильм с id " + id + " не найден!");
    }
}
