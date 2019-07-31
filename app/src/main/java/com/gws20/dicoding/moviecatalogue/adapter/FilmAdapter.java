package com.gws20.dicoding.moviecatalogue.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;

import java.util.List;

public class FilmAdapter extends BaseAdapter {

    private List<FilmEntity> listFilm;

    public void setListFilm(List<FilmEntity> listFilm) {
        this.listFilm = listFilm;
    }

    @Override
    public int getCount() {
        if(listFilm!=null) return this.listFilm.size();
        else return 0;
    }

    @Override
    public Object getItem(int position) {
        return this.listFilm.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) this.listFilm.get(position).getId();
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(GWS20.getInstance()).inflate(R.layout.item_katalog, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(view);
        FilmEntity film = (FilmEntity) getItem(position);
        viewHolder.bind(film);
        return view;
    }

    private class ViewHolder {
        private TextView txtSubject;
        private TextView txtDescription;
        private ImageView imgFilm;
        ViewHolder(View view) {
            txtSubject = view.findViewById(R.id.txt_subject);
            txtDescription = view.findViewById(R.id.txt_desc);
            imgFilm = view.findViewById(R.id.item_image);
        }
        void bind(FilmEntity film) {
            txtSubject.setText(film.getSubject());
            txtDescription.setText(film.getDesc());
            Glide.with(GWS20.getInstance()).load(film.getImg())
                    .apply(new RequestOptions().override(60,60))
                    .into(imgFilm);
        }
    }
}
