package com.gws20.dicoding.moviecatalogue.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.activity.DetailActivity;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;

import java.util.List;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<FilmEntity> listFilm;
    private OnItemClickListener listener;

    public FilmAdapter(){
        this.inflater = LayoutInflater.from(GWS20.getInstance());
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClickListener(View v, int position);
    }

    public void setListFilm(List<FilmEntity> listFilm) {
        this.listFilm = listFilm;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_katalog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        FilmEntity current = listFilm.get(position);
        viewHolder.txtSubject.setText(current.getTitle());
        viewHolder.txtDescription.setText(current.getOverview());
        Glide.with(GWS20.getInstance()).load(String.format(Api.IMG_HOST,Api.SIZE.W_92,current.getPoster_path()))
                .apply(new RequestOptions().override(60,60))
                .into(viewHolder.imgFilm);
    }

    @Override
    public long getItemId(int position) {
        return (long) this.listFilm.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if(listFilm!=null) return this.listFilm.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSubject;
        private TextView txtDescription;
        private ImageView imgFilm;
        ViewHolder(View view) {
            super(view);
            txtSubject = view.findViewById(R.id.txt_subject);
            txtDescription = view.findViewById(R.id.txt_desc);
            imgFilm = view.findViewById(R.id.item_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v,getAdapterPosition());
                }
            });
        }
    }
}
