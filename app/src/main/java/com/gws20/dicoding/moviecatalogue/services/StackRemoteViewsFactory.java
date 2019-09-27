package com.gws20.dicoding.moviecatalogue.services;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.AdapterView;
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

import static com.gws20.dicoding.moviecatalogue.provider.MovieContentProvider.URI_MOVIE;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private Cursor mCursor;

    public StackRemoteViewsFactory(Context context) {
        mContext = context;
    }


    private void initCursor() {
        if (mCursor != null) {
            mCursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        String[] projection = {FilmEntity.TITLE, FilmEntity.POSTER_PATH};
        mCursor = mContext.getContentResolver().query(URI_MOVIE, projection, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        initCursor();
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
        mCursor.moveToPosition(position);
        rv.setTextViewText(R.id.txt_title, mCursor.getString(mCursor.getColumnIndex(FilmEntity.TITLE)));
        try {
            Bitmap bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(String.format(Api.IMG_HOST,Api.SIZE.W_342,mCursor.getString(mCursor.getColumnIndex(FilmEntity.POSTER_PATH))))
                    .submit(200, 200)
                    .get();

            rv.setImageViewBitmap(R.id.img_widget, bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
