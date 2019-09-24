package com.gws20.dicoding.moviecatalogue.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

@Entity
public class TVEntity {
    @Ignore
    public final static String ID = "id";
    @Ignore
    public final static String ORI_NAME = "original_name";
    @Ignore
    public final static String NAME = "name";
    @Ignore
    public final static String VOTE_COUNT = "vote_count";
    @Ignore
    public final static String FIRST_AIR_DATE = "first_air_date";
    @Ignore
    public final static String BACKDROP_PATH = "backdrop_path";
    @Ignore
    public final static String VOTE_AVG = "vote_average";
    @Ignore
    public final static String OVERVIEW = "overview";
    @Ignore
    public final static String POSTER_PATH = "poster_path";
    @Ignore
    public final static String STATUS = "status";
    @Ignore
    public final static String TYPE = "type";
    @Ignore
    public final static String CREDITS = "credits";
    @Ignore
    public final static String GENRES = "genres";
    @Ignore
    public final static String CAST = "cast";
    @Ignore
    public final static String CREW = "crew";
    @Ignore
    public final static String PROD_COMPANIES = "production_companies";

    @PrimaryKey
    private int id;
    @Ignore
    private String original_name;
    private String name;
    @Ignore
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
    @Ignore
    private List<String> genres;
    @Ignore
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

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_name(String original_name) {
        this.original_name = original_name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setVote_average(long vote_average) {
        this.vote_average = vote_average;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setProduction_companies(String production_companies) {
        this.production_companies = production_companies;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setCast(List<PeopleEntity> cast) {
        this.cast = cast;
    }

}
