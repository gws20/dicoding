package com.gws20.dicoding.moviecatalogue.provider;

import android.arch.persistence.room.Database;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.room.MainDatabase;

public class MovieContentProvider extends ContentProvider {
    public static final String AUTHORITY = "com.gws20.dicoding.moviecatalogue.provider";
    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + FilmEntity.TABLE_NAME);
    private static final int CODE_MOVIE_FAVORITE = 1;
    private final UriMatcher MATCHER;
    private MainDatabase database;

    public MovieContentProvider() {
        MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        MATCHER.addURI(AUTHORITY, FilmEntity.TABLE_NAME,CODE_MOVIE_FAVORITE);
    }

    @Override
    public boolean onCreate() {
        database = MainDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        final int code = MATCHER.match(uri);
        if (code == CODE_MOVIE_FAVORITE) {
            final Context context = getContext();
            if (context == null) {
                return null;
            }
            Cursor cursor = database.movieFavoriteDao().getMovieListWidget();
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (MATCHER.match(uri) == CODE_MOVIE_FAVORITE) {
            return "vnd.android.cursor.fav/" + AUTHORITY + "." + FilmEntity.TABLE_NAME;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
