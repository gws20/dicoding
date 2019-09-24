package com.gws20.dicoding.moviecatalogue.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ProgressBar;

import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.activity.DetailActivity;
import com.gws20.dicoding.moviecatalogue.adapter.FilmAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieFragment extends Fragment {
    private ProgressBar mProgressBar;

    private int mActivityCode;

    public static MovieFragment newInstance(int activityCode) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(GWS20.ARG_ACTIVITY, activityCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mActivityCode = getArguments().getInt(GWS20.ARG_ACTIVITY);
        }else mActivityCode = 0;
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listFilmView = view.findViewById(R.id.list_film);
        mProgressBar = view.findViewById(R.id.progress_loader);
        mProgressBar.setVisibility(View.VISIBLE);
        final FilmAdapter adapter = new FilmAdapter();
        listFilmView.setAdapter(adapter);
        listFilmView.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        switch (mActivityCode) {
            case 0:
                movieViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<FilmEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<FilmEntity> filmEntities) {
                        adapter.setListFilm(filmEntities);
                        adapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(getContext(), DetailActivity.class);
                                assert filmEntities != null;
                                intent.putExtra(Api.MOVIE, filmEntities.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
                break;
            case 1:
                movieViewModel.getFavoriteList().observe(getViewLifecycleOwner(), new Observer<List<FilmEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<FilmEntity> filmEntities) {
                        adapter.setListFilm(filmEntities);
                        adapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(getContext(), DetailActivity.class);
                                assert filmEntities != null;
                                intent.putExtra(Api.MOVIE, filmEntities.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }
}
