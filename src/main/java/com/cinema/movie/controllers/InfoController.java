package com.cinema.movie.controllers;

import com.cinema.movie.models.MathExpectation;
import com.cinema.movie.models.MovieList;
import com.cinema.movie.models.Movies;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("info")
public class InfoController {
    MovieList movieList = new MovieList();
    MathExpectation mathExpectation = new MathExpectation();

    @GetMapping
    public LinkedHashMap<String, Object> info(HttpServletRequest request) throws FileNotFoundException {
        LinkedHashMap<String, Object> info = new LinkedHashMap<String, Object>();
        movieList.readYaml();
        List<Movies> cinemaList = movieList.getCinemaList();
        String username = request.getHeader("username");

        if (!Objects.equals(username, null)) {
            info.put("Приветствие", "Привет, " + username);
        }
        info.put("ФИО автора", "Багдасарян Тигран Егишеевич");
        info.put("Вариант", 9);
        info.put("Предметная область", "Кинотеатр");

        if (!cinemaList.isEmpty()) {
            double mean = mathExpectation.calculateMathExpectation(cinemaList);
            double variance = mathExpectation.calculateVarianceTicketPrice(cinemaList, mean);

            info.put("Математическое ожидание цен на билеты", String.format("%.2f рублей", mean));
            info.put("Дисперсия цен на билеты", String.format("%.2f рублей^2", variance));
        }
        return info;
    }
}
