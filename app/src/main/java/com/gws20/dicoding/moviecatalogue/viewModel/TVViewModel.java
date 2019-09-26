package com.gws20.dicoding.moviecatalogue.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.repository.MovieRepository;
import com.gws20.dicoding.moviecatalogue.repository.TVRepository;

import java.util.List;

public class TVViewModel extends AndroidViewModel {
    private TVRepository mRepository;
    private MutableLiveData<List<TVEntity>> mList;
    private MutableLiveData<TVEntity> mDetail;
    public TVViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TVRepository(application);
        mList = new MutableLiveData<>();
        mDetail = new MutableLiveData<>();
    }

    public MutableLiveData<List<TVEntity>> getList(){
        mList = mRepository.getList();
        return mList;
    }

    public LiveData<List<TVEntity>> getSearchList(String query){
        return mRepository.getSearchList(query);
    }

    public MutableLiveData<TVEntity> getDetail(int id){
        mDetail = mRepository.getDetail(id);
        return mDetail;
    }

    public LiveData<List<TVEntity>> getFavoriteList(){
        return mRepository.getFavoriteList();
    }

    public LiveData<Long> setFavorite(TVEntity tv){
        return mRepository.setFavorite(tv);
    }

    public LiveData<Integer> deleteFavorite(int id){
        return mRepository.deleteFavorite(id);
    }

    public LiveData<Integer> isFavorite(int id){
        return mRepository.isFavorite(id);
    }
}
