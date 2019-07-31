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
import com.gws20.dicoding.moviecatalogue.entity.CastEntity;
import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {
    private List<CastEntity> listCast;
    private LayoutInflater inflater;

    public CastAdapter(){
        this.inflater = LayoutInflater.from(GWS20.getInstance());
    }

    public void setData(List<CastEntity> list){
        this.listCast = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = this.inflater.inflate(R.layout.item_cast, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CastEntity current = listCast.get(position);
        viewHolder.txtCast.setText(current.getName());
        if(current.getImg()!=null){
            Glide.with(GWS20.getInstance()).load(current.getImg())
                    .apply(new RequestOptions().override(50,50))
                    .into(viewHolder.imgCast);
        }
    }

    @Override
    public long getItemId(int position) {
        return this.listCast.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if(listCast!=null) return listCast.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCast;
        private ImageView imgCast;
        ViewHolder(View view) {
            super(view);
            txtCast = view.findViewById(R.id.txt_cast);
            imgCast = view.findViewById(R.id.img_cast);
        }
    }
}
