package com.gws20.dicoding.moviecatalogue.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;

@Database(entities = {FilmEntity.class, TVEntity.class}, version = 1)
public abstract class MainDatabase extends RoomDatabase {
    private static final String DB_NAME = "gws20.db";

    public abstract MovieFavoriteDao movieFavoriteDao();
    public abstract TVFavoriteDao tvFavoriteDao();

    private static MainDatabase INSTANCE;

    public static MainDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MainDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MainDatabase.class, DB_NAME)
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
