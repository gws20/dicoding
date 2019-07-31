package com.gws20.dicoding.moviecatalogue.activity;

import android.os.Bundle;
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
import com.gws20.dicoding.moviecatalogue.Dataset;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.adapter.CastAdapter;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView subject =findViewById(R.id.txt_subject);
        TextView produser = findViewById(R.id.txt_produser);
        TextView sutradara = findViewById(R.id.txt_sutradara);
        TextView penulis = findViewById(R.id.txt_penulis);
        TextView produksi = findViewById(R.id.txt_produksi);
        TextView description = findViewById(R.id.txt_desc);
        RecyclerView listCast = findViewById(R.id.list_cast);
        ImageView img = findViewById(R.id.img_film);
        TagView<String> tag_film = findViewById(R.id.tagview);

        FilmEntity film = getIntent().getParcelableExtra(Dataset.FILM);
        if(film!=null){
            subject.setText(film.getSubject());
            produser.setText(film.getProduser());
            sutradara.setText(film.getSutradara());
            penulis.setText(film.getPenulis());
            produksi.setText(film.getProduksi());
            description.setText(film.getDesc());
            tag_film.setTags(film.getJenis(), new DataTransform<String>() {
                @NotNull
                @Override
                public String transfer(String s) {
                    return s;
                }
            });
            CastAdapter adapter = new CastAdapter();
            listCast.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
            listCast.setAdapter(adapter);
            adapter.setData(film.getCast());
            Glide.with(this).load(film.getImg()).into(img);
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
