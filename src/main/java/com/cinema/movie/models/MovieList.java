package com.cinema.movie.models;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cinema.movie"})
public class MovieList {

    private List<Movies> cinemaList = new ArrayList<>();

    public void readYaml() throws FileNotFoundException {
        cinemaList.clear();
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(new File("cinema.yaml"));
        Map<String, Object> obj = yaml.load(inputStream);
        ArrayList<Map<String, Object>> cinema;
        cinema = (ArrayList<Map<String, Object>>) obj.get("cinema");

        double totalProbability = 0.0;

        for (Map<String, Object> movieData : cinema) {
            if (movieData != null) {
                String id = (String) movieData.get("id");
                String movieName = (String) movieData.get("movieName");
                String startTime = (String) movieData.get("startTime");
                String duration = (String) movieData.get("duration");

                Object priceObj = movieData.get("ticketPrice");
                double ticketPrice = 0.0;

                if (priceObj instanceof Double) {
                    ticketPrice = (Double) priceObj;
                } else if (priceObj instanceof String) {
                    ticketPrice = Double.parseDouble((String) priceObj);
                }

                Object probabilityObj = movieData.get("probability");
                double probability = 0.0;

                if (probabilityObj instanceof Double) {
                    probability = (Double) probabilityObj;
                } else if (probabilityObj instanceof String) {
                    probability = Double.parseDouble((String) probabilityObj);
                }

                Movies movie = new Movies(id, movieName, startTime, duration, ticketPrice, probability);
                movie.setId(id);

                totalProbability += probability;

                cinemaList.add(movie);
            }
        }

        // Если вероятности были сгенерированы, нормализуем их

        for (Movies movie : cinemaList) {
            movie.setProbability(movie.getProbability() / totalProbability);
        }
    }

    public void saveYaml() throws IOException {
        Map<String, Object> data = new HashMap<>();
        ArrayList<Map<String, Object>> cinema = new ArrayList<>();

        for (Movies movie : cinemaList) {
            Map<String, Object> movieData = new HashMap<>();
            movieData.put("id", movie.getId());
            movieData.put("movieName", movie.getMovieName());
            movieData.put("startTime", movie.getStartTime());
            movieData.put("duration", movie.getDuration());
            movieData.put("ticketPrice", movie.getTicketPrice());
            movieData.put("probability", movie.getProbability());

            cinema.add(movieData);
        }

        data.put("cinema", cinema);

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);

        Yaml yaml = new Yaml(options);
        FileWriter writer = new FileWriter("cinema.yaml");
        yaml.dump(data, writer);
    }

    public List<Movies> getCinemaList() {
        return cinemaList;
    }

    public Movies getCinemaListById(String id) {
        return cinemaList.stream()
                .filter(movie -> movie.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void deleteMovie(String id) {
        Movies movie = getCinemaListById(id);
        if (movie != null) {
            cinemaList.remove(movie);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieList.class, args);
    }
}
