package com.example.a1_23l_0981;

public class Movie {
    private String title;
    private String genreDuration;
    private String poster;
    private String trailerUrl;
    private boolean isNowShowing;

    public Movie(String title, String genreDuration, String poster, String trailerUrl, boolean isNowShowing) {
        this.title = title;
        this.genreDuration = genreDuration;
        this.poster = poster;
        this.trailerUrl = trailerUrl;
        this.isNowShowing = isNowShowing;
    }

    public String getTitle() { return title; }
    public String getGenreDuration() { return genreDuration; }
    public String getPoster() { return poster; }
    public String getTrailerUrl() { return trailerUrl; }
    public boolean isNowShowing() { return isNowShowing; }
}