package com.gws20.dicoding.moviecatalogue.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MovieRepository {
    private Application mAplication;
    private MutableLiveData<List<FilmEntity>> mList;
    private MutableLiveData<FilmEntity> mDetail;

    public MovieRepository(Application application){
        mAplication=application;
        mList = new MutableLiveData<>();
        mDetail = new MutableLiveData<>();
        AndroidNetworking.initialize(application);
    }

    public MutableLiveData<List<FilmEntity>> getList(){
        AndroidNetworking.get(Api.HOST_MOVIE)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                           mList.postValue(Api.parseMovies(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mAplication,"Error Network, Please check your connection",Toast.LENGTH_LONG).show();
                    }
                });
        return mList;
    }

    public MutableLiveData<FilmEntity> getDetail(int id){
        AndroidNetworking.get(Api.API_DETAIL_HOST)
                .addPathParameter(Api.JENIS,Api.MOVIE)
                .addPathParameter(Api.ID, String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mDetail.postValue(Api.parseDetailMovie(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mAplication,"Error Network, Please check your connection",Toast.LENGTH_LONG).show();
                    }
                });
        return mDetail;
    }


}
