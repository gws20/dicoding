package com.gws20.dicoding.favoritemovieapp.activity;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.gws20.dicoding.favoritemovieapp.R;
import com.gws20.dicoding.favoritemovieapp.adapter.FilmAdapter;
import com.gws20.dicoding.favoritemovieapp.entity.FilmEntity;

public class MainActivity extends AppCompatActivity {
    public static final String AUTHORITY = "com.gws20.dicoding.moviecatalogue.provider";
    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + FilmEntity.TABLE_NAME);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.list_film);
        ProgressBar progressBar = findViewById(R.id.progress_loader);
        String[] projection = { FilmEntity.POSTER_PATH, FilmEntity.TITLE, FilmEntity.OVERVIEW};
        Cursor cursor = getContentResolver().query(URI_MOVIE, projection, null, null, null);
        FilmAdapter adapter = new FilmAdapter(this,cursor);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
