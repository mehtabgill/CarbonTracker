package ca.cmpt276.carbontracker.UI;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.TaskStackBuilder;
import android.util.Log;

/**
 * Created by RoronoaZoro on 2017-04-08.
 */

public class Notifications extends IntentService {

    public static final String EXTRA_MESSAGE = "message";
    public static final int NOTIFICATION_ID = 2100;

    public Notifications(){
        super("Notifications");
    }

    @Override
            protected void onHandleIntent(Intent intent){
        synchronized (this){
            try{
                wait(10000);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra(EXTRA_MESSAGE);
        showText(text);
    }




private void showText(final String text) {
    Intent intent = new Intent(this, SelectTransportationMode.class);
    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

    stackBuilder.addParentStack(SelectTransportationMode.class);
    stackBuilder.addNextIntent(intent);

    PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


    //Create a notification Builder
    Notification notification = new Notification.Builder(this)
            .setSmallIcon(R.mipmap.notification)
            .setContentTitle("My test notification")
            .setContentText("Hello there World")
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(pendingIntent)
            .build();

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, notification);
}
}
