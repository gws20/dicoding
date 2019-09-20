package com.gws20.dicoding.moviecatalogue;

import android.content.Context;
import android.content.Intent;

import com.gws20.dicoding.moviecatalogue.entity.CastEntity;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Dataset {
    private final static String FILENAME = "dataset.json";
    public final static String FILM = "movie";
    public final static String TV = "tv";
    private ArrayList<FilmEntity> mListFilm;
    private ArrayList<TVEntity> mListTV;

    public Dataset(){
        mListFilm = new ArrayList<>();
        mListTV = new ArrayList<>();
        try {
            mListFilm = parseFilm(loadJSONFromAsset(GWS20.getInstance()));
            mListTV = parseTV(loadJSONFromAsset(GWS20.getInstance()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FilmEntity> getListFilm() {
        return mListFilm;
    }
    public ArrayList<TVEntity> getListTV() {
        return mListTV;
    }

    private String loadJSONFromAsset(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open(FILENAME);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private static ArrayList<FilmEntity> parseFilm(String source) throws JSONException {
        ArrayList<FilmEntity> list = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(source).getJSONArray(FILM);
        for(int i=0;i < jsonArray.length();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            List<String> jenis = new ArrayList<>();
            JSONArray jenisArray = object.getJSONArray(FilmEntity.JENIS);
            for(int j=0;j<jenisArray.length();j++){
                jenis.add(jenisArray.getString(j));
            }
            FilmEntity film = new FilmEntity(
                    jsonArray.getJSONObject(i).getInt(FilmEntity.ID),
                    getStringFromJSON(object,FilmEntity.SUBJECT),
                    getStringFromJSON(object,FilmEntity.DESC),
                    getStringFromJSON(object,FilmEntity.IMG),
                    getStringFromJSON(object,FilmEntity.TIME),
                    getStringFromJSON(object,FilmEntity.KATEGORI),
                    getStringFromJSON(object,FilmEntity.PRODUSER),
                    getStringFromJSON(object,FilmEntity.PRODUKSI),
                    getStringFromJSON(object,FilmEntity.SUTRADARA),
                    getStringFromJSON(object,FilmEntity.PENULIS),
                    jenis,
                    parseCast(object.getJSONArray(FilmEntity.CAST))
            );
            list.add(film);
        }
        return list;
    }

    private static ArrayList<TVEntity> parseTV(String source) throws JSONException {
        ArrayList<TVEntity> list = new ArrayList<>();
        JSONArray jsonArray = new JSONObject(source).getJSONArray(TV);
        for(int i=0;i < jsonArray.length();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            List<String> jenis = new ArrayList<>();
            JSONArray jenisArray = object.getJSONArray(TVEntity.JENIS);
            for(int j=0;j<jenisArray.length();j++){
                jenis.add(jenisArray.getString(j));
            }
            TVEntity film = new TVEntity(
                    jsonArray.getJSONObject(i).getInt(TVEntity.ID),
                    getStringFromJSON(object,TVEntity.SUBJECT),
                    getStringFromJSON(object,TVEntity.DESC),
                    getStringFromJSON(object,TVEntity.IMG),
                    getLongFromJSON(object,TVEntity.TIME_BEGIN),
                    getLongFromJSON(object,TVEntity.TIME_END),
                    getStringFromJSON(object,TVEntity.KATEGORI),
                    getStringFromJSON(object,TVEntity.PRODUSER),
                    getStringFromJSON(object,TVEntity.PRODUKSI),
                    getStringFromJSON(object,TVEntity.SUTRADARA),
                    getStringFromJSON(object,TVEntity.PENULIS),
                    jenis,
                    parseCast(object.getJSONArray(TVEntity.CAST))
            );
            list.add(film);
        }
        return list;
    }

    private static List<CastEntity> parseCast(JSONArray array) throws JSONException {
        List<CastEntity> list = new ArrayList<>();
        for (int i=0;i<array.length();i++){
            JSONObject object = array.getJSONObject(i);
            list.add(new CastEntity(
                    object.getInt(CastEntity.ID),
                    getStringFromJSON(object,CastEntity.NAME),
                    getStringFromJSON(object,CastEntity.IMG)
            ));
        }
        return list;
    }

    private static String getStringFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?(!jsonObject.getString(key).equals("null")?jsonObject.getString(key):null):null;
    }

    private static long getLongFromJSON(JSONObject jsonObject, String key) throws JSONException {
        return jsonObject.has(key)?jsonObject.getLong(key):0;
    }

    private static Object getValueFromJSON(JSONObject jsonObject, String key) throws JSONException{
        Object object = jsonObject.get(key);
        if(object instanceof String)
            return jsonObject.has(key)?(!jsonObject.getString(key).equals("null")?jsonObject.getString(key):null):null;
        else if(object instanceof Integer)
            return jsonObject.has(key)?jsonObject.getInt(key):0;
        else if(object instanceof Long)
            return jsonObject.has(key)?jsonObject.getLong(key):0;
        else if(object instanceof Double)
            return jsonObject.has(key)?jsonObject.getDouble(key):0;
        else if(object instanceof Boolean)
            return jsonObject.has(key) && jsonObject.getBoolean(key);
        return null;
    }
}
