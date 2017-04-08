package ca.cmpt276.carbontracker.UI;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.TaskStackBuilder;
import android.util.Log;

import ca.cmpt276.carbontracker.Model.SingletonModel;

/**
 * This class displays notifications to user
 */

public class Notifications extends IntentService {

    //public static final String EXTRA_MESSAGE = "s";
    public static final int NOTIFICATION_ID = 2100;

    public Notifications(){
        super("Notifications");
    }

    @Override
            protected void onHandleIntent(Intent intent){
        synchronized (this){
            try{
                wait(10);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        String text = intent.getStringExtra("msg");
        Log.i("TESTING", text);
        showText(text);
    }




private void showText(String text) {

    PendingIntent pendingIntent;

    if(text.contains("Journeys")) {
        Intent intent = new Intent(this, SelectTransportationMode.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        //stackBuilder.addParentStack(SelectTransportationMode.class);
        stackBuilder.addNextIntentWithParentStack(intent);

        pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    else{
        Intent intent = new Intent(this, AddUtilitiesBillActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(AddUtilitiesBillActivity.class);
        stackBuilder.addNextIntent(intent);

        pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    //Create a notification Builder
    Notification notification = new Notification.Builder(this)
            .setSmallIcon(R.mipmap.notification)
            .setContentTitle("Carbon Tracker")
            .setContentText(text)
            .setAutoCancel(true)
            .setPriority(Notification.PRIORITY_MAX)
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setContentIntent(pendingIntent)
            .build();

    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.notify(NOTIFICATION_ID, notification);


}
}
