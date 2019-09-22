package com.gws20.dicoding.moviecatalogue.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TVRepository {
    private Application mAplication;
    private MutableLiveData<List<TVEntity>> mList;
    private MutableLiveData<TVEntity> mDetail;

    public TVRepository(Application application){
        mAplication=application;
        mList = new MutableLiveData<>();
        mDetail = new MutableLiveData<>();
        AndroidNetworking.initialize(application);
    }

    public MutableLiveData<List<TVEntity>> getList(){
        AndroidNetworking.get(Api.HOST_TV)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mList.postValue(Api.parseTV(response));
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

    public MutableLiveData<TVEntity> getDetail(int id){
        AndroidNetworking.get(Api.API_DETAIL_HOST)
                .addPathParameter(Api.JENIS,Api.TV)
                .addPathParameter(Api.ID, String.valueOf(id))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mDetail.postValue(Api.parseDetailTV(response));
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
