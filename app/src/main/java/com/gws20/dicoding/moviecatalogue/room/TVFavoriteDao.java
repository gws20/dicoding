package com.gws20.dicoding.moviecatalogue.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;

import java.util.List;

@Dao
public interface TVFavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void bulkInsert(List<TVEntity> tvEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TVEntity tv);

    @Query("SELECT * FROM TVEntity")
    LiveData<List<TVEntity>> getTVList();

    @Query("DELETE FROM TVEntity WHERE id=:tvId")
    int delete(Integer tvId);

    @Query("SELECT count(id) FROM TVEntity WHERE id=:id")
    LiveData<Integer> getIsFavorite(int id);
}
