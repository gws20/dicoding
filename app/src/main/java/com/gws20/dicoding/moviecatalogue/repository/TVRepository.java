package com.gws20.dicoding.moviecatalogue.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.listeners.OnDeleteListener;
import com.gws20.dicoding.moviecatalogue.listeners.OnInsertListener;
import com.gws20.dicoding.moviecatalogue.room.MainDatabase;
import com.gws20.dicoding.moviecatalogue.room.TVFavoriteDao;
import com.gws20.dicoding.moviecatalogue.utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TVRepository {
    private Application mAplication;
    private MutableLiveData<List<TVEntity>> mList;
    private MutableLiveData<TVEntity> mDetail;
    private TVFavoriteDao mTVDao;

    public TVRepository(Application application){
        MainDatabase db = MainDatabase.getDatabase(application);
        mTVDao = db.tvFavoriteDao();
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

    public LiveData<List<TVEntity>> getFavoriteList(){
        return mTVDao.getTVList();
    }

    public MutableLiveData<Long> setFavorite(TVEntity tv){
        final MutableLiveData<Long> data = new MutableLiveData<>();
        new insertAsyncTask(mTVDao, new OnInsertListener() {
            @Override
            public void OnSuccess(Long idList) {
                data.postValue(idList);
            }

            @Override
            public void OnFailed(String message) {
                Toast.makeText(mAplication,message,Toast.LENGTH_LONG).show();
            }
        }).execute(tv);
        return data;
    }

    public MutableLiveData<Integer> deleteFavorite(int id){
        final MutableLiveData<Integer> data = new MutableLiveData<>();
        new deleteAsyncTask(mTVDao, new OnDeleteListener() {
            @Override
            public void OnSuccess(int id) {
                data.postValue(id);
            }

            @Override
            public void OnFailed(String message) {
                Toast.makeText(mAplication,message,Toast.LENGTH_LONG).show();
            }
        }).execute(id);
        return data;
    }

    public LiveData<Integer> isFavorite(int id){
        return mTVDao.getIsFavorite(id);
    }

    private static class insertAsyncTask extends AsyncTask<TVEntity, Void, Long> {

        private TVFavoriteDao mAsyncTaskDao;
        private OnInsertListener mListener;

        insertAsyncTask(TVFavoriteDao dao, OnInsertListener listener) {
            mAsyncTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected Long doInBackground(final TVEntity... params) {
            return mAsyncTaskDao.insert(params[0]);
        }

        @Override
        protected void onPostExecute(Long longs) {
            super.onPostExecute(longs);
            mListener.OnSuccess(longs);
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private TVFavoriteDao mAsyncTaskDao;
        private OnDeleteListener mListener;

        deleteAsyncTask(TVFavoriteDao dao, OnDeleteListener listener) {
            mAsyncTaskDao = dao;
            mListener=listener;
        }

        @Override
        protected Integer doInBackground(final Integer... params) {
            mAsyncTaskDao.delete(params[0]);
            return mAsyncTaskDao.delete(params[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            mListener.OnSuccess(integer);
        }
    }
}
