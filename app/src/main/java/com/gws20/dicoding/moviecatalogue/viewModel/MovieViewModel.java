package com.gws20.dicoding.moviecatalogue.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository mRepository;
    private MutableLiveData<List<FilmEntity>> mList;
    private MutableLiveData<FilmEntity> mDetail;
    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mList = new MutableLiveData<>();
        mDetail = new MutableLiveData<>();
    }

    public MutableLiveData<List<FilmEntity>> getList(){
        mList = mRepository.getList();
        return mList;
    }

    public LiveData<List<FilmEntity>> getSearchList(String query){
        return mRepository.getSearchList(query);
    }

    public MutableLiveData<FilmEntity> getDetail(int id){
        mDetail = mRepository.getDetail(id);
        return mDetail;
    }

    public LiveData<List<FilmEntity>> getFavoriteList(){
        return mRepository.getFavoriteList();
    }

    public LiveData<Long> setFavorite(FilmEntity film){
        return mRepository.setFavorite(film);
    }

    public LiveData<Integer> deleteFavorite(int id){
        return mRepository.deleteFavorite(id);
    }

    public LiveData<Integer> isFavorite(int id){
        return mRepository.isFavorite(id);
    }
}
