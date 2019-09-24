package com.gws20.dicoding.moviecatalogue.fragment;

import android.arch.lifecycle.Observer;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.activity.DetailActivity;
import com.gws20.dicoding.moviecatalogue.adapter.FilmAdapter;
import com.gws20.dicoding.moviecatalogue.adapter.TVAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class TVFragment extends Fragment {
    private ProgressBar mProgressBar;

    private int mActivityCode;

    public static TVFragment newInstance(int activityCode) {
        TVFragment fragment = new TVFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listTVView = view.findViewById(R.id.list_tv);
        mProgressBar = view.findViewById(R.id.progress_loader);
        mProgressBar.setVisibility(View.VISIBLE);
        final TVAdapter adapter = new TVAdapter();
        listTVView.setAdapter(adapter);
        listTVView.setLayoutManager(new LinearLayoutManager(getContext()));
        TVViewModel tvViewModel = ViewModelProviders.of(this).get(TVViewModel.class);
        switch (mActivityCode){
            case 0 :
                tvViewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<TVEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<TVEntity> tvEntities) {
                        adapter.setListTV(tvEntities);
                        adapter.setOnItemClickListener(new TVAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(getContext(), DetailActivity.class);
                                intent.putExtra(Api.TV, tvEntities.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
                break;
            case 1:
                tvViewModel.getFavoriteList().observe(getViewLifecycleOwner(), new Observer<List<TVEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<TVEntity> tvEntities) {
                        adapter.setListTV(tvEntities);
                        adapter.setOnItemClickListener(new TVAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(getContext(), DetailActivity.class);
                                intent.putExtra(Api.TV, tvEntities.get(position).getId());
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
