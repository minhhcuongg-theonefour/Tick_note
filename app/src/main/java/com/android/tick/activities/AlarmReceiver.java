package com.android.tick.activities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.tick.R;

public class AlarmReceiver extends BroadcastReceiver {
    String title, subtitle;

    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(
                new Intent(context, CreateNoteActivity.class).putExtra("broadCastIntent",intent)
        );


//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
//            Toast.makeText(context, "Alarm just rang", Toast.LENGTH_SHORT).show();
//        }
//
        Intent i =new Intent(context, CreateNoteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"tick")
                .setSmallIcon(R.mipmap.ic_launcher_foreground)
                .setContentTitle("You have a task")
                .setContentText("You have a task")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
        Toast.makeText(context, "Broadcast receiver called", Toast.LENGTH_SHORT).show();
    }
}
