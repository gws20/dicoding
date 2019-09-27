package com.gws20.dicoding.moviecatalogue.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.gws20.dicoding.moviecatalogue.GWS20;
import com.gws20.dicoding.moviecatalogue.Preferences.PreferencesManager;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.receivers.RemainderReceiver;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;

import timber.log.Timber;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SwitchPreference mDailyPreference;
    private SwitchPreference mReleasePreference;

    private String DAILY;
    private String RELEASE;
    private MovieViewModel mMovieViewModel;

    private PreferencesManager mManager;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        mManager = PreferencesManager.getInstance();
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        init();
        setSummaries();
    }

    private void init(){
        DAILY = getString(R.string.key_daily);
        RELEASE = getString(R.string.key_release);

        mDailyPreference = (SwitchPreference) findPreference(DAILY);
        mReleasePreference = (SwitchPreference) findPreference(RELEASE);
    }
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        RemainderReceiver remainderReceiver = new RemainderReceiver();

        if (key.equals(DAILY)) {
            boolean value = sharedPreferences.getBoolean(DAILY, (Boolean) mManager.get(DAILY));
            mDailyPreference.setChecked(value);
            if(value) remainderReceiver.setAlarm(GWS20.getInstance(),RemainderReceiver.REQUEST_CODE_DAILY,
                    RemainderReceiver.HOURS_DAILY, mMovieViewModel);
            else remainderReceiver.cancelAlarm(GWS20.getInstance(),RemainderReceiver.REQUEST_CODE_DAILY);
        }
        if (key.equals(RELEASE)) {
            boolean value = sharedPreferences.getBoolean(RELEASE, (Boolean) mManager.get(RELEASE));
            mReleasePreference.setChecked(value);
            if(value) remainderReceiver.setAlarm(GWS20.getInstance(),RemainderReceiver.REQUEST_CODE_RELEASE,
                    RemainderReceiver.HOURS_RELEASE, mMovieViewModel);
            else remainderReceiver.cancelAlarm(GWS20.getInstance(),RemainderReceiver.REQUEST_CODE_RELEASE);
        }

    }
    private void setSummaries() {
        mDailyPreference.setChecked((Boolean) mManager.get(DAILY));
        mReleasePreference.setChecked((Boolean) mManager.get(RELEASE));
    }
}