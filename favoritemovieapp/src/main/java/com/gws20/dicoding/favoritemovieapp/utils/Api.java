package com.gws20.dicoding.favoritemovieapp.utils;

import com.gws20.dicoding.favoritemovieapp.entity.FilmEntity;
import com.gws20.dicoding.favoritemovieapp.entity.PeopleEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api {
    public final static String API_HOST = "https://api.themoviedb.org/3/%s/%s?api_key=07d29b89e107ae225810a189159b21a3&language=en-US"; //{jenis}
    public final static String API_DETAIL_HOST = "https://api.themoviedb.org/3/{jenis}/{id}?api_key=07d29b89e107ae225810a189159b21a3&language=en-US&append_to_response=credits"; //{jenis,id}
    public final static String IMG_HOST = "https://image.tmdb.org/t/p/%s/%s"; //path param={size,name}
    public final static String API_HOST_EXCLUDE_LANG = "https://api.themoviedb.org/3/discover/movie?api_key=07d29b89e107ae225810a189159b21a3";

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


}
