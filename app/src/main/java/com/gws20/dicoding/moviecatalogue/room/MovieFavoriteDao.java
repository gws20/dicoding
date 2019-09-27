package com.gws20.dicoding.moviecatalogue.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

import java.util.List;

@Dao
public interface MovieFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<FilmEntity> filmEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(FilmEntity film);

    @Query("SELECT * FROM FilmEntity")
    LiveData<List<FilmEntity>> getMovieList();

    @Query("SELECT * FROM FilmEntity")
    Cursor getMovieListWidget();

    @Query("DELETE FROM FilmEntity WHERE id=:movieId")
    int delete(Integer movieId);

    @Query("SELECT count(id) FROM FilmEntity WHERE id=:id")
    LiveData<Integer> getIsFavorite(int id);
}
