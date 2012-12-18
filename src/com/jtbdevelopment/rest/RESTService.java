package com.jtbdevelopment.rest;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by IntelliJ IDEA.
 * JTB Development
 * Date: 7/28/12
 * Time: 5:10 PM
 */
public class RESTService extends IntentService {

    public RESTService() {
        super(RESTService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("R.E.S.T.", "REST Service kicked");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        String message = PreferenceManager.getDefaultSharedPreferences(this).getString("alarmText", this.getString(R.string.defaultAlarmText));
        builder.setContentTitle(message);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.alerticon));
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        builder.setVibrate(new long[]{100, 200, 100, 500, 100, 500});
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.alerticon);
        Intent notificationIntent = new Intent(this, AlarmViewer.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.getNotification();
        notificationManager.notify(1, notification);
    }
}
