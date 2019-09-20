package com.gws20.dicoding.moviecatalogue.adapter;

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
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.entity.TVEntity;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<TVEntity> listTV;
    private TVAdapter.OnItemClickListener listener;

    public TVAdapter(){
        this.inflater = LayoutInflater.from(GWS20.getInstance());
    }

    public void setOnItemClickListener(TVAdapter.OnItemClickListener listener){
        this.listener=listener;
    }

    public interface OnItemClickListener{
        void onItemClickListener(View v, int position);
    }

    public void setListTV(List<TVEntity> listTV) {
        this.listTV = listTV;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TVAdapter.ViewHolder(this.inflater.inflate(R.layout.item_katalog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.ViewHolder viewHolder, int position) {
        TVEntity current = listTV.get(position);
        viewHolder.txtSubject.setText(current.getSubject());
        viewHolder.txtDescription.setText(current.getDesc());
        Glide.with(GWS20.getInstance()).load(current.getImg())
                .apply(new RequestOptions().override(60,60))
                .into(viewHolder.imgFilm);
    }

    @Override
    public long getItemId(int position) {
        return (long) this.listTV.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if(listTV !=null) return this.listTV.size();
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
