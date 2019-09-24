package com.gws20.dicoding.moviecatalogue.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.tommykw.tagview.DataTransform;
import com.github.tommykw.tagview.TagView;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.adapter.CastAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;
import com.gws20.dicoding.moviecatalogue.viewModel.TVViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private TextView mSubject;
    private TextView mProduser;
    private TextView mSutradara;
    private TextView mPenulis;
    private TextView mProduksi;
    private TextView mDescription;
    private RecyclerView mListCast;
    private ImageView mImg;
    private TagView<String> mTagFilm;
    private ProgressBar mProgressBar;
    private ImageView mFavoriteIC;
    private TextView mFavoriteTxt;

    MovieViewModel mMovieViewModel;
    TVViewModel mTVViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mSubject =findViewById(R.id.txt_subject);
        mProduser = findViewById(R.id.txt_produser);
        mSutradara = findViewById(R.id.txt_sutradara);
        mPenulis = findViewById(R.id.txt_penulis);
        mProduksi = findViewById(R.id.txt_produksi);
        mDescription = findViewById(R.id.txt_desc);
        mListCast = findViewById(R.id.list_cast);
        mImg = findViewById(R.id.img_film);
        mTagFilm = findViewById(R.id.tagview);
        mProgressBar = findViewById(R.id.progress_loader);
        mFavoriteIC = findViewById(R.id.ic_favorite);
        mFavoriteTxt = findViewById(R.id.txt_favorite);
        mProgressBar.setVisibility(View.VISIBLE);

        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        mTVViewModel = ViewModelProviders.of(this).get(TVViewModel.class);

        int id = getIntent().getIntExtra(Api.MOVIE,-1);
        Boolean isMovie = id != -1;
        if(isMovie) mMovieViewModel.getDetail(id).observe(this, new Observer<FilmEntity>() {
            @Override
            public void onChanged(@Nullable final FilmEntity film) {
                mSubject.setText(film.getTitle());
                mProduser.setText(film.getProducer());
                mSutradara.setText(film.getDirector());
                mPenulis.setText(film.getWriter());
                mProduksi.setText(film.getProduction_companies());
                mDescription.setText(film.getOverview());
                mTagFilm.setTags(film.getGenres(), new DataTransform<String>() {
                    @NotNull
                    @Override
                    public String transfer(String s) {
                        return s;
                    }
                });
                CastAdapter adapter = new CastAdapter();
                mListCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL, false));
                mListCast.setAdapter(adapter);
                adapter.setData(film.getCast());
                Glide.with(DetailActivity.this).load(String.format(Api.IMG_HOST,Api.SIZE.W_342,film.getPoster_path())).into(mImg);
                mProgressBar.setVisibility(View.GONE);
                mFavoriteTxt.setText("-");
                mMovieViewModel.isFavorite(getIntent().getIntExtra(Api.MOVIE,-1))
                        .observe(DetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        UIFavorite(integer,film);
                    }
                });
            }
        });
        else {
            id = getIntent().getIntExtra(Api.TV,-1);
            mTVViewModel.getDetail(id).observe(this, new Observer<TVEntity>() {
                @Override
                public void onChanged(@Nullable final TVEntity tv) {
                    mSubject.setText(tv.getName());
                    mProduser.setText(tv.getProducer());
                    mSutradara.setText(tv.getDirector());
                    mPenulis.setText(tv.getWriter());
                    mProduksi.setText(tv.getProduction_companies());
                    mDescription.setText(tv.getOverview());
                    mTagFilm.setTags(tv.getGenres(), new DataTransform<String>() {
                        @NotNull
                        @Override
                        public String transfer(String s) {
                            return s;
                        }
                    });
                    CastAdapter adapter = new CastAdapter();
                    mListCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this,LinearLayoutManager.HORIZONTAL, false));
                    mListCast.setAdapter(adapter);
                    adapter.setData(tv.getCast());
                    Glide.with(DetailActivity.this).load(String.format(Api.IMG_HOST,Api.SIZE.W_342,tv.getPoster_path())).into(mImg);
                    mProgressBar.setVisibility(View.GONE);
                    mFavoriteTxt.setText("-");
                    mTVViewModel.isFavorite(getIntent().getIntExtra(Api.TV,-1))
                            .observe(DetailActivity.this, new Observer<Integer>() {
                                @Override
                                public void onChanged(@Nullable Integer integer) {
                                    UIFavorite(integer,tv);
                                }
                            });
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void UIFavorite(int isFavorite, Object data){
        if(isFavorite==0){
            mFavoriteIC.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_red_600_24dp));
            mFavoriteTxt.setText(getString(R.string.saved_favorite));
            mFavoriteIC.setOnClickListener(new notFavClick(data));
            mFavoriteTxt.setOnClickListener(new notFavClick(data));
        }else {
            mFavoriteIC.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
            mFavoriteTxt.setText(getString(R.string.delete_favorite));
            mFavoriteIC.setOnClickListener(new favClick(data));
            mFavoriteTxt.setOnClickListener(new favClick(data));
        }
        mFavoriteIC.setVisibility(View.VISIBLE);
    }

    private class notFavClick implements View.OnClickListener{
        FilmEntity mFilm;
        TVEntity mTV;

        private notFavClick(Object data){
            if(data instanceof FilmEntity) mFilm = (FilmEntity) data;
            else if(data instanceof TVEntity) mTV = (TVEntity) data;
        }
        @Override
        public void onClick(View v) {
            mFavoriteTxt.setText(getString(R.string.saving));
            if(mFilm!=null){//movie
                mMovieViewModel.setFavorite(mFilm).observe(DetailActivity.this, new Observer<Long>() {
                    @Override
                    public void onChanged(@Nullable Long longs) {
                        if(longs==0){
                            UIFavorite(0, mFilm);
                            Toast.makeText(DetailActivity.this,"Failed to set favorite",Toast.LENGTH_LONG).show();
                        }else {
                            UIFavorite(1, mFilm);
                        }
                    }
                });
            }else mTVViewModel.setFavorite(mTV).observe(DetailActivity.this, new Observer<Long>() {
                @Override
                public void onChanged(@Nullable Long longs) {
                    UIFavorite(1, mTV);
                }
            });
        }
    }

    private class favClick implements View.OnClickListener{
        FilmEntity mFilm;
        TVEntity mTV;

        private favClick(Object data){
            if(data instanceof FilmEntity) mFilm = (FilmEntity) data;
            else if(data instanceof TVEntity) mTV = (TVEntity) data;
        }
        @Override
        public void onClick(View v) {
            mFavoriteTxt.setText(getString(R.string.saving));
            if(mFilm!=null){//movie
                mMovieViewModel.deleteFavorite(mFilm.getId()).observe(DetailActivity.this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer integer) {
                        UIFavorite(0, mFilm);
                    }
                });
            }else mTVViewModel.deleteFavorite(mTV.getId()).observe(DetailActivity.this, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer integer) {
                    UIFavorite(0, mTV);
                }
            });
        }
    }
}
