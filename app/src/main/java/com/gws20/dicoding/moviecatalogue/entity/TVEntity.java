package com.gws20.dicoding.moviecatalogue.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TVEntity {
    public final static String ID = "id";
    public final static String ORI_NAME = "original_name";
    public final static String NAME = "name";
    public final static String VOTE_COUNT = "vote_count";
    public final static String FIRST_AIR_DATE = "first_air_date";
    public final static String BACKDROP_PATH = "backdrop_path";
    public final static String VOTE_AVG = "vote_average";
    public final static String OVERVIEW = "overview";
    public final static String POSTER_PATH = "poster_path";
    public final static String STATUS = "status";
    public final static String TYPE = "type";
    public final static String CREDITS = "credits";
    public final static String GENRES = "genres";
    public final static String CAST = "cast";
    public final static String CREW = "crew";
    public final static String PROD_COMPANIES = "production_companies";

    private int id;
    private String original_name;
    private String name;
    private int vote_count;
    private String first_air_date;
    private String backdrop_path;
    private long vote_average;
    private String overview;
    private String poster_path;
    private String status;
    private String type;
    private String producer;
    private String director;
    private String writer;
    private String production_companies;
    private List<String> genres;
    private List<PeopleEntity> cast;

    public TVEntity(int id, String poster_path, String name, long vote_average, String overview) {
        this.id = id;
        this.name = name;
        this.overview = overview;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
    }

    public TVEntity(int id, String name, String first_air_date, String backdrop_path,
                    long vote_average, String overview, String poster_path, String status,
                    String type, String producer, String director, String writer,
                    String production_companies, List<String> genres, List<PeopleEntity> cast) {
        this.id = id;
        this.name = name;
        this.first_air_date = first_air_date;
        this.backdrop_path = backdrop_path;
        this.vote_average = vote_average;
        this.overview = overview;
        this.poster_path = poster_path;
        this.status = status;
        this.type = type;
        this.producer = producer;
        this.director = director;
        this.writer = writer;
        this.production_companies = production_companies;
        this.genres = genres;
        this.cast = cast;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public String getName() {
        return name;
    }

    public int getVote_count() {
        return vote_count;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public long getVote_average() {
        return vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
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

    public String getProduction_companies() {
        return production_companies;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<PeopleEntity> getCast() {
        return cast;
    }
}
