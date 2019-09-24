package com.gws20.dicoding.moviecatalogue.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.fragment.MovieFragment;
import com.gws20.dicoding.moviecatalogue.fragment.TVFragment;

public class TabAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private int mActivityCode;

    public TabAdapter(FragmentManager fm, Context context, int activityCode) {
        super(fm);
        mContext=context;
        mActivityCode=activityCode;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return MovieFragment.newInstance(mActivityCode);
            case 1:
                return TVFragment.newInstance(mActivityCode);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.tab_movie);
            case 1:
                return mContext.getString(R.string.tab_tv);
            default:
                return null;
        }
    }
}
