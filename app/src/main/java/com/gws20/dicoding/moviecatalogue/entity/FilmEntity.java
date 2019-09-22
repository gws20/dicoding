package com.gws20.dicoding.moviecatalogue.entity;

import java.util.List;

public class FilmEntity {
    public final static java.lang.String ID = "id";
    public final static java.lang.String POPULARITY = "popularity";
    public final static java.lang.String VOTE_COUNT = "vote_count";
    public final static java.lang.String VIDEO = "video";
    public final static java.lang.String POSTER_PATH = "poster_path";
    public final static java.lang.String ADULT = "adult";
    public final static java.lang.String BACKDROP_PATH = "backdrop_path";
    public final static java.lang.String ORI_LANG = "original_language";
    public final static java.lang.String ORI_TITLE = "original_title";
    public final static java.lang.String TITLE = "title";
    public final static java.lang.String VOTE_AVG = "vote_average";
    public final static java.lang.String OVERVIEW = "overview";
    public final static java.lang.String RELEASE_DATE = "release_date";

    public final static java.lang.String GENRES = "genres";
    public final static java.lang.String BUDGET = "budget";
    public final static java.lang.String HOMEPAGE = "homepage";
    public final static java.lang.String PROD_COMPANIES = "production_companies";
    public final static java.lang.String REVENUE = "revenue";
    public final static java.lang.String RUNTIME = "runtime";
    public final static java.lang.String SPOKEN_LANG = "spoken_languages";
    public final static java.lang.String STATUS = "status";
    public final static java.lang.String TAGLINE = "tagline";
    public final static java.lang.String CREDITS = "credits";
    public final static java.lang.String CAST = "cast";
    public final static java.lang.String CREW = "crew";
    public final static java.lang.String NAME = "name";
    public final static java.lang.String PROFILE_PATH = "profile_path";
    public final static java.lang.String JOB = "job";

    private int id;
    private long popularity;
    private int vote_count;
    private Boolean video;
    private java.lang.String poster_path;
    private Boolean adult;
    private java.lang.String backdrop_path;
    private java.lang.String original_language;
    private java.lang.String original_title;
    private java.lang.String title;
    private long vote_average;
    private java.lang.String overview;
    private java.lang.String release_date;

    private List<String> genres;
    private int budget;
    private java.lang.String homepage;
    private java.lang.String production_companies;
    private int revenue;
    private int runtime;
    private List<LanguageEntity> spoken_languages;
    private java.lang.String status;
    private java.lang.String tagline;
    private List<PeopleEntity> cast;
    private java.lang.String producer;
    private java.lang.String director;
    private java.lang.String writer;

    public FilmEntity(int id, java.lang.String poster_path, java.lang.String title, long vote_average, java.lang.String overview) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
    }

    public FilmEntity(int id, int vote_count, java.lang.String poster_path, Boolean adult, java.lang.String backdrop_path,
                      java.lang.String title, long vote_average, java.lang.String overview, java.lang.String release_date,
                      List<String> genres, int budget, java.lang.String homepage,
                      java.lang.String production_companies, int revenue, int runtime,
                      java.lang.String status, java.lang.String tagline, List<PeopleEntity> cast, java.lang.String producer,
                      java.lang.String director, java.lang.String writer) {
        this.id = id;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.adult = adult;
        this.backdrop_path = backdrop_path;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.genres = genres;
        this.budget = budget;
        this.homepage = homepage;
        this.production_companies = production_companies;
        this.revenue = revenue;
        this.runtime = runtime;
        this.status = status;
        this.tagline = tagline;
        this.cast = cast;
        this.producer = producer;
        this.director = director;
        this.writer = writer;
    }

    public int getId() {
        return id;
    }

    public long getPopularity() {
        return popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public Boolean getVideo() {
        return video;
    }

    public java.lang.String getPoster_path() {
        return poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public java.lang.String getBackdrop_path() {
        return backdrop_path;
    }

    public java.lang.String getOriginal_language() {
        return original_language;
    }

    public java.lang.String getOriginal_title() {
        return original_title;
    }

    public java.lang.String getTitle() {
        return title;
    }

    public long getVote_average() {
        return vote_average;
    }

    public java.lang.String getOverview() {
        return overview;
    }

    public java.lang.String getRelease_date() {
        return release_date;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getBudget() {
        return budget;
    }

    public java.lang.String getHomepage() {
        return homepage;
    }

    public java.lang.String getProduction_companies() {
        return production_companies;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<LanguageEntity> getSpoken_languages() {
        return spoken_languages;
    }

    public java.lang.String getStatus() {
        return status;
    }

    public java.lang.String getTagline() {
        return tagline;
    }

    public List<PeopleEntity> getCast() {
        return cast;
    }

    public java.lang.String getProducer() {
        return producer;
    }

    public java.lang.String getDirector() {
        return director;
    }

    public java.lang.String getWriter() {
        return writer;
    }
}
