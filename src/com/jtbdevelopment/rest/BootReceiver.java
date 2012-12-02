package com.jtbdevelopment.rest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by IntelliJ IDEA.
 * JTB Development
 * Date: 7/16/12
 * Time: 9:00 PM
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            return;
        }
        kickOffReminderService(context);
    }

    public static void kickOffReminderService(final Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, RESTReceiver.class);
        intent.putExtra(RESTReceiver.STARTUP_FLAG, true);
        intent.putExtra(RESTReceiver.INTENT_MAKER, BootReceiver.class.getSimpleName());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
    }
}
