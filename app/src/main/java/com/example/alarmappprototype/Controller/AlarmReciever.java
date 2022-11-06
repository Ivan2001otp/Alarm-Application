package com.example.alarmappprototype.Controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.alarmappprototype.MainActivity;
import com.example.alarmappprototype.R;

public class AlarmReciever extends BroadcastReceiver {
    public AlarmReciever(){

    }


    //starting the service.


    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.danza_kuduro);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();



        Log.d("alarm", "onReceive: activated");
       // context.startActivity(intent);
        Log.d("alarm", "onReceive: deactivated");
        if(getAbortBroadcast()){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer  = null;
        }







        Intent i = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"notification")
                .setSmallIcon(R.drawable.alarm_clock)
                .setContentTitle("Alarm App")
                .setContentText("Your alarm is Activated!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());

    }




}
