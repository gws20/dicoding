package com.gws20.dicoding.favoritemovieapp.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class FilmEntity {
    @Ignore
    public final static String TABLE_NAME = "FilmEntity";
    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String POPULARITY = "popularity";
    @Ignore
    public final static String VOTE_COUNT = "vote_count";
    @Ignore
    public final static String VIDEO = "video";
    @Ignore
    public final static String POSTER_PATH = "poster_path";
    @Ignore
    public final static String ADULT = "adult";
    @Ignore
    public final static String BACKDROP_PATH = "backdrop_path";
    @Ignore
    public final static String ORI_LANG = "original_language";
    @Ignore
    public final static String ORI_TITLE = "original_title";
    @Ignore
    public final static String TITLE = "title";
    @Ignore
    public final static String VOTE_AVG = "vote_average";
    @Ignore
    public final static String OVERVIEW = "overview";
    @Ignore
    public final static String RELEASE_DATE = "release_date";

    @Ignore
    public final static String GENRES = "genres";
    @Ignore
    public final static String BUDGET = "budget";
    @Ignore
    public final static String HOMEPAGE = "homepage";
    @Ignore
    public final static String PROD_COMPANIES = "production_companies";
    @Ignore
    public final static String REVENUE = "revenue";
    @Ignore
    public final static String RUNTIME = "runtime";
    @Ignore
    public final static String SPOKEN_LANG = "spoken_languages";
    @Ignore
    public final static String STATUS = "status";
    @Ignore
    public final static String TAGLINE = "tagline";
    @Ignore
    public final static String CREDITS = "credits";
    @Ignore
    public final static String CAST = "cast";
    @Ignore
    public final static String CREW = "crew";
    @Ignore
    public final static String NAME = "name";
    @Ignore
    public final static String PROFILE_PATH = "profile_path";
    @Ignore
    public final static String JOB = "job";

    @PrimaryKey
    private int id;
    @Ignore
    private long popularity;
    private int vote_count;
    @Ignore
    private Boolean video;
    private String poster_path;
    private Boolean adult;
    private String backdrop_path;
    @Ignore
    private String original_language;
    @Ignore
    private String original_title;
    private String title;
    private long vote_average;
    private String overview;
    private String release_date;

    @Ignore
    private List<String> genres;
    private int budget;
    private String homepage;
    private String production_companies;
    private int revenue;
    private int runtime;
    @Ignore
    private List<LanguageEntity> spoken_languages;
    private String status;
    private String tagline;
    @Ignore
    private List<PeopleEntity> cast;
    private String producer;
    private String director;
    private String writer;

    public FilmEntity(String poster_path, String title, String overview) {
        this.poster_path = poster_path;
        this.title = title;
        this.overview = overview;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPopularity(long popularity) {
        this.popularity = popularity;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVote_average(long vote_average) {
        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setSpoken_languages(List<LanguageEntity> spoken_languages) {
        this.spoken_languages = spoken_languages;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setCast(List<PeopleEntity> cast) {
        this.cast = cast;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setWriter(String writer) {
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

    public String getPoster_path() {
        return poster_path;
    }

    public Boolean getAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getTitle() {
        return title;
    }

    public long getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public List<String> getGenres() {
        return genres;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getProduction_companies() {
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

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public List<PeopleEntity> getCast() {
        return cast;
    }

    public String getProducer() {
        return producer;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }
}
