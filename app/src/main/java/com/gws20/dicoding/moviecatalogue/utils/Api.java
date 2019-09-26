package com.gws20.dicoding.moviecatalogue.utils;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.PeopleEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api {
    public final static String API_HOST = "https://api.themoviedb.org/3/%s/%s?api_key=07d29b89e107ae225810a189159b21a3&language=en-US"; //{jenis}
    public final static String API_DETAIL_HOST = "https://api.themoviedb.org/3/{jenis}/{id}?api_key=07d29b89e107ae225810a189159b21a3&language=en-US&append_to_response=credits"; //{jenis,id}
    public final static String IMG_HOST = "https://image.tmdb.org/t/p/%s/%s"; //path param={size,name}

    public final static String MOVIE = "movie";
    public final static String TV = "tv";
    public final static String DISCOVER = "discover";
    public final static String SEARCH = "search";
    public final static String QUERY = "query";
    public final static String ID = "id";
    public final static String JENIS = "jenis";
    public final static String NAME = "name";

    public final static class SIZE {
        public final static String W_ORIGINAL = "original";
        public final static String W_92 = "w92";
        public final static String W_154 = "w154";
        public final static String W_185 = "w185";
        public final static String W_342 = "w342";
        public final static String W_500 = "w500";
        public final static String W_780 = "w780";
    }

    public final static String HOST_MOVIE = String.format(API_HOST,DISCOVER,MOVIE);
    public final static String HOST_TV = String.format(API_HOST,DISCOVER,TV);
    public final static String HOST_SEARCH_MOVIE = String.format(API_HOST,SEARCH,MOVIE);
    public final static String HOST_SEARCH_TV = String.format(API_HOST,SEARCH,TV);

    //API Constants
    public final static String RESULTS = "results";


    public static List<FilmEntity> parseMovies(JSONObject jsonObject) throws JSONException {
        List<FilmEntity> list = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray(RESULTS);
        for(int i=0;i<results.length();i++){
            JSONObject object = results.getJSONObject(i);
            FilmEntity filmEntity = new FilmEntity(object.getInt(FilmEntity.ID),
                    getStringFromJSON(object,FilmEntity.POSTER_PATH),
                    getStringFromJSON(object,FilmEntity.TITLE),
                    getIntFromJSON(object,FilmEntity.VOTE_AVG),
                    getStringFromJSON(object,FilmEntity.OVERVIEW));
            list.add(filmEntity);
        }
        return list;
    }

    private static List<String> getGenres(JSONArray genresArr) throws JSONException {
        List<String> genres = new ArrayList<>();
        for(int i=0;i<genresArr.length();i++){
            JSONObject item = genresArr.getJSONObject(i);
            genres.add(item.getString(FilmEntity.NAME));
        }
        return genres;
    }
    private static List<String> getGenresString(JSONArray genresArr) throws JSONException {
        List<String> genres = new ArrayList<>();
        for(int i=0;i<genresArr.length();i++){
            JSONObject item = genresArr.getJSONObject(i);
            genres.add(item.getString(FilmEntity.NAME));
        }
        return genres;
    }

    private static String getProdCompany(JSONArray compArr) throws JSONException {
        StringBuilder production_companies = new StringBuilder();
        for(int i=0;i<compArr.length();i++){
            String item = compArr.getJSONObject(i).getString(FilmEntity.NAME);
            production_companies.append(i == 0 ? item : ", " + item);
        }
        return production_companies.toString();
    }

    private static List<PeopleEntity> getCastList(JSONArray castArr) throws JSONException {
        List<PeopleEntity> cast = new ArrayList<>();
        for(int i=0; i<castArr.length();i++){
            JSONObject item = castArr.getJSONObject(i);
            PeopleEntity peopleEntity = new PeopleEntity(item.getInt(FilmEntity.ID),
                    getStringFromJSON(item,FilmEntity.NAME),
                    getStringFromJSON(item,FilmEntity.PROFILE_PATH), "cast");
            cast.add(peopleEntity);
        }
        return cast;
    }

    private static class Crew {
        String producer;
        String director;
        String writer;

        Crew(JSONArray crewArr) throws JSONException {
            StringBuilder producer = new StringBuilder();
            StringBuilder director = new StringBuilder();
            StringBuilder writer = new StringBuilder();
            for(int i=0; i<crewArr.length();i++){
                JSONObject item = crewArr.getJSONObject(i);
                switch (item.getString(FilmEntity.JOB).toLowerCase()){
                    case "producer":
                        producer.append(producer.toString().equals("") ? item.getString(FilmEntity.NAME) : ", " + item.getString(FilmEntity.NAME));
                        break;
                    case "director":
                        director.append(director.toString().equals("") ? item.getString(FilmEntity.NAME) : ", " + item.getString(FilmEntity.NAME));
                        break;
                    case "writer":
                        writer.append(writer.toString().equals("") ? item.getString(FilmEntity.NAME) : ", " + item.getString(FilmEntity.NAME));
                        break;
                }
            }
            this.director = director.toString();
            this.producer = producer.toString();
            this.writer = writer.toString();
        }

        String getProducer() {
            return producer;
        }

        String getDirector() {
            return director;
        }

         String getWriter() {
            return writer;
        }
    }

    public static FilmEntity parseDetailMovie(JSONObject object) throws JSONException {
        Crew crew = new Crew(object.getJSONObject(FilmEntity.CREDITS).getJSONArray(FilmEntity.CREW));
        return new FilmEntity(object.getInt(FilmEntity.ID),
                getIntFromJSON(object,FilmEntity.VOTE_COUNT),
                getStringFromJSON(object,FilmEntity.POSTER_PATH),
                getBooleanFromJSON(object,FilmEntity.ADULT),
                getStringFromJSON(object,FilmEntity.BACKDROP_PATH),
                getStringFromJSON(object,FilmEntity.TITLE),
                getLongFromJSON(object,FilmEntity.VOTE_AVG),
                getStringFromJSON(object,FilmEntity.OVERVIEW),
                getStringFromJSON(object,FilmEntity.RELEASE_DATE),
                getGenres(object.getJSONArray(FilmEntity.GENRES)),
                getIntFromJSON(object,FilmEntity.BUDGET),
                getStringFromJSON(object,FilmEntity.HOMEPAGE),
                getProdCompany(object.getJSONArray(FilmEntity.PROD_COMPANIES)),
                getIntFromJSON(object,FilmEntity.REVENUE),
                getIntFromJSON(object,FilmEntity.RUNTIME),
                getStringFromJSON(object,FilmEntity.STATUS),
                getStringFromJSON(object,FilmEntity.TAGLINE),
                getCastList(object.getJSONObject(FilmEntity.CREDITS).getJSONArray(FilmEntity.CAST)),
                crew.getProducer(),
                crew.getDirector(),
                crew.getWriter());
    }

    public static List<TVEntity> parseTV(JSONObject jsonObject) throws JSONException {
        List<TVEntity> list = new ArrayList<>();
        JSONArray results = jsonObject.getJSONArray(RESULTS);
        for(int i=0;i<results.length();i++){
            JSONObject object = results.getJSONObject(i);
            TVEntity tvEntity = new TVEntity(object.getInt(TVEntity.ID),
                    getStringFromJSON(object,TVEntity.POSTER_PATH),
                    getStringFromJSON(object,TVEntity.NAME),
                    getIntFromJSON(object,TVEntity.VOTE_AVG),
                    getStringFromJSON(object,TVEntity.OVERVIEW));
            list.add(tvEntity);
        }
        return list;
    }
    public static TVEntity parseDetailTV(JSONObject object) throws JSONException {
        Crew crew = new Crew(object.getJSONObject(TVEntity.CREDITS).getJSONArray(TVEntity.CREW));

        return new TVEntity(object.getInt(TVEntity.ID),
                getStringFromJSON(object,TVEntity.NAME),
                getStringFromJSON(object,TVEntity.NAME),
                getStringFromJSON(object,TVEntity.BACKDROP_PATH),
                getLongFromJSON(object,TVEntity.VOTE_AVG),
                getStringFromJSON(object,TVEntity.OVERVIEW),
                getStringFromJSON(object,TVEntity.POSTER_PATH),
                getStringFromJSON(object,TVEntity.STATUS),
                getStringFromJSON(object,TVEntity.TYPE),
                crew.getProducer(),
                crew.getDirector(),
                crew.getWriter(),
                getProdCompany(object.getJSONArray(TVEntity.PROD_COMPANIES)),
                getGenres(object.getJSONArray(TVEntity.GENRES)),
                getCastList(object.getJSONObject(TVEntity.CREDITS).getJSONArray(TVEntity.CAST)));
    }


    private static String getStringFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?(!jsonObject.getString(key).equals("null")?jsonObject.getString(key):null):null;
    }

    private static int getIntFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?jsonObject.getInt(key):0;
    }

    private static long getLongFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?jsonObject.getLong(key):0;
    }
    private static Boolean getBooleanFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?jsonObject.getBoolean(key):false;
    }
}
