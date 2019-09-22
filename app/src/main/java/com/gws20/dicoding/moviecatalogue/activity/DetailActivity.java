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
import android.widget.ImageView;
import android.widget.TextView;

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

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        TVViewModel tvViewModel = ViewModelProviders.of(this).get(TVViewModel.class);

        int id = getIntent().getIntExtra(Api.MOVIE,-1);
        Boolean isMovie = id != -1;
        if(isMovie) movieViewModel.getDetail(id).observe(this, new Observer<FilmEntity>() {
            @Override
            public void onChanged(@Nullable FilmEntity film) {
                //if(id>=0){
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
                /*}/*else {
                    id = getIntent().getIntExtra(Dataset.TV,-1);
                    if(id>=0) {
                        TVEntity tv = new Dataset().getListTV().get(id);
                        mSubject.setText(tv.getSubject());
                        mProduser.setText(tv.getProduser());
                        mSutradara.setText(tv.getSutradara());
                        mPenulis.setText(tv.getPenulis());
                        mProduksi.setText(tv.getProduksi());
                        mDescription.setText(tv.getDesc());
                        mTagFilm.setTags(tv.getJenis(), new DataTransform<String>() {
                            @NotNull
                            @Override
                            public String transfer(String s) {
                                return s;
                            }
                        });
                        CastAdapter adapter = new CastAdapter();
                        mListCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                        mListCast.setAdapter(adapter);
                        adapter.setData(tv.getCast());
                        Glide.with(this).load(tv.getImg()).into(mImg);
                    }
                }*/
            }
        });
        else {
            id = getIntent().getIntExtra(Api.TV,-1);
            tvViewModel.getDetail(id).observe(this, new Observer<TVEntity>() {
                @Override
                public void onChanged(@Nullable TVEntity tv) {
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
}
