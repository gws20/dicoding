package com.gws20.dicoding.favoritemovieapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gws20.dicoding.favoritemovieapp.R;
import com.gws20.dicoding.favoritemovieapp.entity.FilmEntity;
import com.gws20.dicoding.favoritemovieapp.utils.Api;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private LayoutInflater inflater;

    private Cursor cursor;
    private Context mContext;

    public FilmAdapter(Context context,Cursor cursor){
        this.inflater = LayoutInflater.from(context);
        this.cursor = cursor;
        mContext=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(this.inflater.inflate(R.layout.item_katalog, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        cursor.moveToPosition(position);
        FilmEntity current = new FilmEntity(
                cursor.getString(cursor.getColumnIndex(FilmEntity.POSTER_PATH)),
                cursor.getString(cursor.getColumnIndex(FilmEntity.TITLE)),
                cursor.getString(cursor.getColumnIndex(FilmEntity.OVERVIEW))
        );
        viewHolder.txtSubject.setText(current.getTitle());
        viewHolder.txtDescription.setText(current.getOverview());
        Glide.with(mContext).load(String.format(Api.IMG_HOST,Api.SIZE.W_92,current.getPoster_path()))
                .apply(new RequestOptions().override(60,60))
                .into(viewHolder.imgFilm);
    }

    @Override
    public int getItemCount() {
        return cursor != null ? cursor.getCount() : 0;
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
        }
    }
}
