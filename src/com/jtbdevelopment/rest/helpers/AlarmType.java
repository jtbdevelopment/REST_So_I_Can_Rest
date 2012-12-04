package com.jtbdevelopment.rest.helpers;

import android.app.PendingIntent;

/**
* Date: 12/3/12
* Time: 10:42 PM
*/
public enum AlarmType {
    BOOT(0, PendingIntent.FLAG_ONE_SHOT),
    SNOOZE(2, PendingIntent.FLAG_ONE_SHOT),
    ALARM(1, PendingIntent.FLAG_CANCEL_CURRENT);

    private final int alarmType;
    private final int flag;

    AlarmType(int alarmType, int flag) {
        this.alarmType = alarmType;
        this.flag = flag;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public int getFlag() {
        return flag;
    }
}
