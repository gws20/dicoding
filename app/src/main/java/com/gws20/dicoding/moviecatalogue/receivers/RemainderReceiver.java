package com.gws20.dicoding.moviecatalogue.receivers;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.gws20.dicoding.moviecatalogue.R;
import com.gws20.dicoding.moviecatalogue.activity.DetailActivity;
import com.gws20.dicoding.moviecatalogue.activity.MainActivity;
import com.gws20.dicoding.moviecatalogue.entity.FilmEntity;
import com.gws20.dicoding.moviecatalogue.utils.Api;
import com.gws20.dicoding.moviecatalogue.viewModel.MovieViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RemainderReceiver extends BroadcastReceiver {
    public final static int REQUEST_CODE_DAILY = 100;
    public final static int REQUEST_CODE_RELEASE = 101;
    public final static String CHANNEL_DAILY = "channel_daily";
    public final static String CHANNEL_RELEASE = "channel_release";
    public final static String CHANNEL_DAILY_NAME = "channel daily";
    public final static String CHANNEL_RELEASE_NAME = "channel release";
    public final static String REQUEST_CODE = "request_code";
    public final static String TIME = "time";
    public final static int HOURS_DAILY = 7;
    public final static int HOURS_RELEASE = 8;

    MovieViewModel mMovieViewModel;

    @Override
    public void onReceive(final Context context, Intent intent) {
        switch (intent.getIntExtra(REQUEST_CODE,0)){
            case REQUEST_CODE_DAILY:
                sendNotification(context,CHANNEL_DAILY,CHANNEL_DAILY_NAME,200,
                        context.getString(R.string.app_name),context.getString(R.string.txt_daily),"",0);
                break;
            case REQUEST_CODE_RELEASE:
                long time = intent.getLongExtra(TIME,0);
                Date date = new Date(time);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                AndroidNetworking.initialize(context);
                AndroidNetworking.get(Api.API_HOST_EXCLUDE_LANG)
                        .addQueryParameter("primary_release_date.gte",sdf.format(date))
                        .addQueryParameter("primary_release_date.lte",sdf.format(date))
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    List<FilmEntity> filmEntities= Api.parseMovies(response);
                                    for(int i =0;i<filmEntities.size();i++){
                                        sendNotification(context,CHANNEL_RELEASE,CHANNEL_RELEASE_NAME,i+201,
                                                filmEntities.get(i).getTitle(),
                                                context.getString(R.string.txt_release,filmEntities.get(i).getTitle()),
                                                null, filmEntities.get(i).getId());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                Toast.makeText(context,"Error Network, Please check your connection",Toast.LENGTH_LONG).show();
                            }
                        });
                break;
        }

    }

    public void setAlarm(Context context, int request_code, int hoursOfAlarm, MovieViewModel movieViewModel) {
        mMovieViewModel = movieViewModel;
        Calendar cal = Calendar.getInstance();
        final int hours = cal.get(Calendar.HOUR_OF_DAY);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);
        final int minutesOfAlarm = 0;

        long time;
        if(hours<hoursOfAlarm | (hours==hoursOfAlarm & minutesOfAlarm>cal.get(Calendar.MINUTE))){//today
            cal.set(year,month,day,hoursOfAlarm,minutesOfAlarm);
            time=cal.getTimeInMillis();
        }else {//tomorrow
            cal.set(year,month,day,hoursOfAlarm,minutesOfAlarm);
            time = cal.getTimeInMillis()+86400000;
        }


        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, RemainderReceiver.class);
        i.putExtra(REQUEST_CODE,request_code);
        i.putExtra(TIME,time);
        PendingIntent pi = PendingIntent.getBroadcast(context, request_code, i, 0);

        assert am != null;
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, time,
                AlarmManager.INTERVAL_DAY, pi);
    }

    public void cancelAlarm(Context context, int request_code){
        Intent intent = new Intent(context, RemainderReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, request_code, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.cancel(sender);
    }

    public void sendNotification(Context context, String channel_id, String channel_name,
                                 int notif_id, String title, String text, String subText, int idMovie) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent;
        if(idMovie==0) intent = new Intent(context, MainActivity.class);
        else {
            intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Api.MOVIE, idMovie);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channel_id)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(text)
                .setSubText(subText)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(channel_id);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        Notification notification = mBuilder.build();

        if (mNotificationManager != null) {
            mNotificationManager.notify(notif_id, notification);
        }
    }

}
