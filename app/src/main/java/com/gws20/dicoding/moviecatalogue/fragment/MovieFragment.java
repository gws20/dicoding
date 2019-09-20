package com.gws20.dicoding.moviecatalogue.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gws20.dicoding.moviecatalogue.Dataset;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.activity.DetailActivity;
import com.gws20.dicoding.moviecatalogue.adapter.FilmAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment {
    private static final String ARG_MOVIE = "argMovie";

    private List<FilmEntity> mParamMovie;

    public static MovieFragment newInstance() {
        MovieFragment fragment = new MovieFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParamMovie = new Dataset().getListFilm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listFilmView = view.findViewById(R.id.list_film);
        FilmAdapter adapter = new FilmAdapter();
        listFilmView.setAdapter(adapter);
        adapter.setListFilm(mParamMovie);
        listFilmView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(Dataset.FILM, position);
                getContext().startActivity(intent);
            }
        });
    }
}