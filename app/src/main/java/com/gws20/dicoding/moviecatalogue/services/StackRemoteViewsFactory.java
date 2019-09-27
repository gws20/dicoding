package com.gws20.dicoding.moviecatalogue.services;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.listeners.OnRetrieveMovie;
import com.gws20.dicoding.moviecatalogue.room.MainDatabase;
import com.gws20.dicoding.moviecatalogue.room.MovieFavoriteDao;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;
import com.gws20.dicoding.moviecatalogue.widget.FavoriteWidget;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
       /* mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.darth_vader));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.star_wars_logo));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.storm_trooper));*/
        /*mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.starwars));
        mWidgetItems.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.falcon));*/
        Log.d("gws20_widget",mWidgetItems.size()+"wi");
        new queryFavoriteFromDB(mContext, new OnRetrieveMovie() {
            @Override
            public void onRetive(List<FilmEntity> filmEntities) {
                for(FilmEntity item:filmEntities){
                    Glide.with(mContext)
                            .asBitmap()
                            .load(String.format(Api.IMG_HOST,Api.SIZE.W_92,item.getPoster_path()))
                            .into(new CustomTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    mWidgetItems.add(resource);
                                }

                                @Override
                                public void onLoadCleared(@Nullable Drawable placeholder) {
                                }
                            });
                }
            }
        }).execute(true);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putInt(FavoriteWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    static class queryFavoriteFromDB extends AsyncTask<Boolean,Void,List<FilmEntity>>{
        private MovieFavoriteDao mAsyncTaskDao;
        private OnRetrieveMovie mListener;

        queryFavoriteFromDB(Context context, OnRetrieveMovie listener){
            mAsyncTaskDao = MainDatabase.getDatabase(context).movieFavoriteDao();
            mListener = listener;
            Log.d("gws20_widget","bb");
        }

        @Override
        protected List<FilmEntity> doInBackground(Boolean... booleans) {
            Log.d("gws20_widget","do");
            return mAsyncTaskDao.getMovieListWidget();
        }

        @Override
        protected void onPostExecute(List<FilmEntity> filmEntities) {
            super.onPostExecute(filmEntities);
            Log.d("gws20_widget",filmEntities.size()+"");
            mListener.onRetive(filmEntities);
        }
    }
}
