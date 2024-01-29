package com.cinema.movie.models;

public class Movies {
    private String id;
    private String movieName;
    private String startTime;
    private String duration;
    private double ticketPrice;
    private double probability;

    public Movies(String id,String movieName, String startTime, String duration, double ticketPrice, double probability) {
        this.id = id;
        this.movieName = movieName;
        this.startTime = startTime;
        this.duration = duration;
        this.ticketPrice = ticketPrice;
        this.probability = probability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", movieName='" + movieName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", probability=" + probability +
                '}';
    }
}
