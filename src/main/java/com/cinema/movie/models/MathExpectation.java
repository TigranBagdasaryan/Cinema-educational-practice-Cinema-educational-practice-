package com.cinema.movie.models;

import java.util.List;

public class MathExpectation {

    public double calculateMathExpectation(List<Movies> moviesList) {
        if (moviesList.isEmpty()) {
            return 0.0;
        }

        double expectedValue = 0.0;

        for (Movies movie : moviesList) {
            double ticketPrice = movie.getTicketPrice();
            double probability = movie.getProbability();
            expectedValue += ticketPrice * probability;
        }

        return expectedValue;
    }

    public double calculateVarianceTicketPrice(List<Movies> moviesList, double mean) {
        if (moviesList.isEmpty() || moviesList.size() == 1) {
            return 0.0;
        }

        double sumSquaredDeviations = 0.0;
        for (Movies movie : moviesList) {
            double ticketPrice = movie.getTicketPrice();
            double deviation = ticketPrice - mean;
            sumSquaredDeviations += deviation * deviation * movie.getProbability();
        }

        return sumSquaredDeviations;
    }
}
