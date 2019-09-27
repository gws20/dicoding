package com.gws20.dicoding.moviecatalogue.listeners;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

import java.util.List;

public interface OnRetrieveMovie {
    void onRetive(List<FilmEntity> filmEntities);
}
