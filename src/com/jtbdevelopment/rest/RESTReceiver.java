package com.jtbdevelopment.rest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.jtbdevelopment.rest.helpers.NextReminderCalculator;
import org.joda.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * JTB Development
 * Date: 7/16/12
 * Time: 9:18 PM
 */
public class RESTReceiver extends BroadcastReceiver {
    public final static String STARTUP_FLAG = "STARTUP";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final boolean startup = intent.getBooleanExtra(STARTUP_FLAG, true);
        if (!startup) {
            Log.d("R.E.S.T.", "Kicking service from receiver = start false");
            Intent serviceIntent = new Intent(context, RESTService.class);
            context.startService(serviceIntent);
        } else {
            Log.d("R.E.S.T.", "Skipping RESTReceiver = start true");
        }
        setAnAlarm(context, NextReminderCalculator.getNextDailyReminderTime());
    }

    public static void setAnAlarm(final Context context, LocalDateTime alarmTime) {
        Log.d("R.E.S.T.", "Next trigger at " + alarmTime);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent alarmIntent = new Intent(context, RESTReceiver.class);
        alarmIntent.putExtra(STARTUP_FLAG, false);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.toDateTime().getMillis(), pendingIntent);
    }
}
