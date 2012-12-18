package com.jtbdevelopment.rest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.jtbdevelopment.rest.helpers.AlarmType;
import com.jtbdevelopment.rest.helpers.NextReminderCalculator;
import org.joda.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * JTB Development
 * Date: 7/16/12
 * Time: 9:18 PM
 */
public class RESTReceiver extends BroadcastReceiver {
    public final static String ALARM_TYPE = "ALARM_TYPE";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        AlarmType alarmType = AlarmType.valueOf(intent.getStringExtra(ALARM_TYPE));
        Log.d("R.E.S.T.", alarmType.toString() + " alarm");
        switch (alarmType) {
            case ALARM:
                setAnAlarm(context, alarmType, NextReminderCalculator.getNextDailyReminderTime(context));
                fireRESTService(context);
                break;
            case SNOOZE:
                fireRESTService(context);
                break;
            case BOOT:
                setAnAlarm(context, AlarmType.ALARM, NextReminderCalculator.getNextDailyReminderTime(context));
                break;
        }
    }

    private void fireRESTService(final Context context) {
        Intent serviceIntent = new Intent(context, RESTService.class);
        context.startService(serviceIntent);
    }

    public static void setAnAlarm(final Context context, AlarmType alarmType, LocalDateTime alarmTime) {
        Log.d("R.E.S.T.", "Next trigger at " + alarmTime + " of type " + alarmType);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = getPendingIntent(context, alarmType);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime.toDateTime().getMillis(), pendingIntent);
    }

    private static PendingIntent getPendingIntent(final Context context, final AlarmType alarmType) {
        final Intent alarmIntent = new Intent(context, RESTReceiver.class);
        alarmIntent.putExtra(ALARM_TYPE, alarmType.toString());
        return PendingIntent.getBroadcast(context, alarmType.getAlarmType(), alarmIntent, alarmType.getFlag());
    }

    public static void cancelAlarms(final Context context) {
        PendingIntent snoozeIntent = getPendingIntent(context, AlarmType.SNOOZE);
        PendingIntent alarmIntent = getPendingIntent(context, AlarmType.ALARM);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(snoozeIntent);
        alarmManager.cancel(alarmIntent);
    }
}
