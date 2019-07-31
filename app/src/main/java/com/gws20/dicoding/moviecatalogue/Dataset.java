package com.gws20.dicoding.moviecatalogue;

import android.content.Context;

import com.gws20.dicoding.moviecatalogue.entity.CastEntity;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

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
    public final static String FILM = "item_film";
    private List<FilmEntity> mList;

    public Dataset(){
        mList = new ArrayList<>();
        try {
            mList = parseDataset(loadJSONFromAsset(GWS20.getInstance()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<FilmEntity> getList() {
        return mList;
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

    private static List<FilmEntity> parseDataset(String source) throws JSONException {
        List<FilmEntity> list = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(source);
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
}
