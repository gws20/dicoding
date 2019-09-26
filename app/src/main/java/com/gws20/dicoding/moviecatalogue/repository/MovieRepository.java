package com.gws20.dicoding.moviecatalogue.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.listeners.OnDeleteListener;
import com.gws20.dicoding.moviecatalogue.listeners.OnInsertListener;
import com.gws20.dicoding.moviecatalogue.room.MainDatabase;
import com.gws20.dicoding.moviecatalogue.room.MovieFavoriteDao;
import com.gws20.dicoding.moviecatalogue.utils.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MovieRepository {
    private Application mAplication;
    private MutableLiveData<List<FilmEntity>> mList;
    private MutableLiveData<List<FilmEntity>> mSearchList;
    private MutableLiveData<FilmEntity> mDetail;
    private MovieFavoriteDao mMovieDao;

    public MovieRepository(Application application){
        MainDatabase db = MainDatabase.getDatabase(application);
        mMovieDao = db.movieFavoriteDao();
        mAplication=application;
        mList = new MutableLiveData<>();
        mDetail = new MutableLiveData<>();
        mSearchList = new MutableLiveData<>();
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

    public MutableLiveData<List<FilmEntity>> getSearchList(String query){
        AndroidNetworking.get(Api.HOST_SEARCH_MOVIE)
                .addQueryParameter(Api.QUERY,query)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("gws20_search",response.toString());
                        try {
                            mSearchList.postValue(Api.parseMovies(response));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mAplication,"Error Network, Please check your connection",Toast.LENGTH_LONG).show();
                    }
                });
        return mSearchList;
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

    public LiveData<List<FilmEntity>> getFavoriteList(){
        return mMovieDao.getMovieList();
    }

    public MutableLiveData<Long> setFavorite(FilmEntity film){
        final MutableLiveData<Long> data = new MutableLiveData<>();
        new insertAsyncTask(mMovieDao, new OnInsertListener() {
            @Override
            public void OnSuccess(Long idList) {
                data.postValue(idList);
            }

            @Override
            public void OnFailed(String message) {
                Toast.makeText(mAplication,message,Toast.LENGTH_LONG).show();
            }
        }).execute(film);
        return data;
    }

    public MutableLiveData<Integer> deleteFavorite(int id){
        final MutableLiveData<Integer> data = new MutableLiveData<>();
        new deleteAsyncTask(mMovieDao, new OnDeleteListener() {
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
        return mMovieDao.getIsFavorite(id);
    }

    private static class insertAsyncTask extends AsyncTask<FilmEntity, Void, Long> {

        private MovieFavoriteDao mAsyncTaskDao;
        private OnInsertListener mListener;

        insertAsyncTask(MovieFavoriteDao dao, OnInsertListener listener) {
            mAsyncTaskDao = dao;
            mListener = listener;
        }

        @Override
        protected Long doInBackground(final FilmEntity... params) {
            return mAsyncTaskDao.insert(params[0]);
        }

        @Override
        protected void onPostExecute(Long longs) {
            super.onPostExecute(longs);
            mListener.OnSuccess(longs);
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private MovieFavoriteDao mAsyncTaskDao;
        private OnDeleteListener mListener;

        deleteAsyncTask(MovieFavoriteDao dao, OnDeleteListener listener) {
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
