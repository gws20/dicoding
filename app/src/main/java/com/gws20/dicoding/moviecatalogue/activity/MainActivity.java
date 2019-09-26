package com.gws20.dicoding.moviecatalogue.activity;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.adapter.FilmAdapter;
import com.gws20.dicoding.moviecatalogue.adapter.TVAdapter;
import com.gws20.dicoding.moviecatalogue.adapter.TabAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;
import com.gws20.dicoding.moviecatalogue.viewModel.TVViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private TabLayout.OnTabSelectedListener mTabListener;
    private TabLayout mTabView;
    private ViewPager mPager;
    private RecyclerView mSearchList;
    private FilmAdapter mSearchFilmAdapter;
    private TVAdapter mSearchTVAdapter;
    private MovieViewModel mMovieViewModel;
    private TVViewModel mTVViewModel;
    private TextView mNotFoundTxt;
    private ProgressBar mSearchLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMovieViewModel = ViewModelProviders.of(MainActivity.this).get(MovieViewModel.class);
        mTVViewModel = ViewModelProviders.of(MainActivity.this).get(TVViewModel.class);

        mPager = findViewById(R.id.pager_main);
        final TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(),this, 0);
        mPager.setAdapter(tabAdapter);
        mTabView = findViewById(R.id.tab_main);
        mTabView.setupWithViewPager(mPager);
        mTabView.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabListener.onTabSelected(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mTabListener.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mTabListener.onTabReselected(tab);
            }
        });

        //init search list on default tab
        mSearchList = findViewById(R.id.list_search);
        mSearchFilmAdapter = new FilmAdapter();
        mSearchTVAdapter = new TVAdapter();
        mSearchList.setLayoutManager(new LinearLayoutManager(this));
        mSearchList.setAdapter(mSearchFilmAdapter);

        mNotFoundTxt = findViewById(R.id.txt_not_found);
        mSearchLoader = findViewById(R.id.progress_loader);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
                startActivity(intent);
            }
        });

        setUISearchOff();

    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            final SearchView searchView = (SearchView) searchItem.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            //on default tab
            searchView.setQueryHint(getString(R.string.action_search)+" "+ Objects.requireNonNull(mTabView.getTabAt(0)).getText());
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    mSearchList.setAdapter(mSearchFilmAdapter);
                    MainActivity.this.onQueryTextSubmit(query,mTabView.getTabAt(0));
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            //to handle back button
            searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

                @Override
                public boolean onMenuItemActionExpand(MenuItem item) {
                    return true;
                }

                @Override
                public boolean onMenuItemActionCollapse(MenuItem item) {
                    setUISearchOff();
                    return true;
                }
            });

            //handle when tab change
            mTabListener = new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(final TabLayout.Tab tab) {
                    //set adapter when tab changed
                    if(tab.getPosition()==1) mSearchList.setAdapter(mSearchTVAdapter);
                    else mSearchList.setAdapter(mSearchFilmAdapter);

                    searchView.setQueryHint(getString(R.string.action_search)+" "+tab.getText());
                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            MainActivity.this.onQueryTextSubmit(query, tab);
                            return true;
                        }
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            return false;
                        }
                    });
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            };
        }

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem setting = menu.findItem(R.id.action_settings);
        setting.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_language:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUISearchOn(int size){
        mTabView.setVisibility(View.GONE);
        mPager.setVisibility(View.GONE);
        mSearchLoader.setVisibility(View.GONE);
        if(size>0){
            mSearchList.setVisibility(View.VISIBLE);
            mNotFoundTxt.setVisibility(View.GONE);
        }else {
            mSearchList.setVisibility(View.GONE);
            mNotFoundTxt.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this,getString(R.string.txt_not_found),Toast.LENGTH_LONG).show();
        }
    }

    private void setUISearchOff(){
        mTabView.setVisibility(View.VISIBLE);
        mPager.setVisibility(View.VISIBLE);
        mSearchList.setVisibility(View.GONE);
        mSearchLoader.setVisibility(View.GONE);
        mNotFoundTxt.setVisibility(View.GONE);
    }

    private void setUISearchLoading(){
        mTabView.setVisibility(View.GONE);
        mPager.setVisibility(View.GONE);
        mSearchList.setVisibility(View.GONE);
        mSearchLoader.setVisibility(View.VISIBLE);
        mNotFoundTxt.setVisibility(View.GONE);
    }

    private void onQueryTextSubmit(String query, TabLayout.Tab tab){
        setUISearchLoading();
        switch (tab.getPosition()){
            case 0:
                mMovieViewModel.getSearchList(query).observe(MainActivity.this, new Observer<List<FilmEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<FilmEntity> filmEntities) {
                        mSearchFilmAdapter.setListFilm(filmEntities);
                        mSearchFilmAdapter.setOnItemClickListener(new FilmAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra(Api.MOVIE, filmEntities.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        setUISearchOn(filmEntities.size());
                    }
                });
                break;
            case 1:
                mTVViewModel.getSearchList(query).observe(MainActivity.this, new Observer<List<TVEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<TVEntity> tvEntities) {
                        mSearchTVAdapter.setListTV(tvEntities);
                        mSearchTVAdapter.setOnItemClickListener(new TVAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClickListener(View v, int position) {
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra(Api.TV, tvEntities.get(position).getId());
                                startActivity(intent);
                            }
                        });
                        setUISearchOn(tvEntities.size());
                    }
                });
                break;
        }
    }
}
