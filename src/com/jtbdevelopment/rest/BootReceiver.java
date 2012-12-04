package com.jtbdevelopment.rest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.jtbdevelopment.rest.helpers.AlarmType;

/**
 * Created by IntelliJ IDEA.
 * JTB Development
 * Date: 7/16/12
 * Time: 9:00 PM
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d("R.E.S.T.", "Kicking off Bootreceiver");
        kickOffReminderService(context);
    }

    public static void kickOffReminderService(final Context context) {
        AlarmType alarmType = AlarmType.BOOT;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent intent = new Intent(context, RESTReceiver.class);
        intent.putExtra(RESTReceiver.ALARM_TYPE, alarmType.toString());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarmType.getAlarmType(), intent, alarmType.getFlag());
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 10000, pendingIntent);
    }
}
