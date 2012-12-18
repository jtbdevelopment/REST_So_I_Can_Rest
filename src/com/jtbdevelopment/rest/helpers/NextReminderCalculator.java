package com.jtbdevelopment.rest.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.jtbdevelopment.rest.R;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

public class NextReminderCalculator {

    public static LocalTime getReminderTime(final Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String alarmTimeString = prefs.getString("alarmTime", context.getString(R.string.defaultAlarmTime));
        return LocalTime.parse(alarmTimeString);
    }

    private static LocalDateTime adjustToThanksgiving(final LocalDate dateToAdjust, final LocalTime reminderTime) {
        LocalDateTime turkeyDay = dateToAdjust.toLocalDateTime(reminderTime);
        turkeyDay = turkeyDay.withMonthOfYear(DateTimeConstants.NOVEMBER).withDayOfMonth(1);
        while (turkeyDay.getDayOfWeek() != DateTimeConstants.THURSDAY) {
            turkeyDay = turkeyDay.plusDays(1);
        }
        //  At first thursday
        return turkeyDay.plusWeeks(3);
    }

    private static LocalDateTime getThisYearThanksgiving(final LocalTime reminderTime) {
        LocalDate now = new LocalDate();
        return adjustToThanksgiving(now, reminderTime);
    }

    private static LocalDateTime getNextYearThanksgiving(final LocalTime reminderTime) {
        LocalDate now = new LocalDate();
        return adjustToThanksgiving(now.plusYears(1), reminderTime);
    }

    private static LocalDateTime getThisYearChristmasEve(final LocalTime reminderTime) {
        LocalDateTime now = new LocalDate().toLocalDateTime(reminderTime);
        return now.withMonthOfYear(DateTimeConstants.DECEMBER).withDayOfMonth(24);
    }

    public static LocalDateTime getNextDailyReminderTime(final Context context) {
        LocalTime reminderTime = getReminderTime(context);
        LocalDateTime currentYearStart = getThisYearThanksgiving(reminderTime);
        LocalDateTime currentYearEnd = getThisYearChristmasEve(reminderTime);
        LocalDateTime reminderForToday = new LocalDate().toLocalDateTime(reminderTime);
        LocalDateTime now = new LocalDateTime();
        if (reminderForToday.compareTo(now) < 0) {
            reminderForToday = reminderForToday.plusDays(1);
        }
        if (reminderForToday.compareTo(currentYearEnd) > 0) {
            //  This Christmas is over (Dec 24 reminder time thru Dec 31)
            return getNextYearThanksgiving(reminderTime);
        } else if (reminderForToday.compareTo(currentYearStart) < 0) {
            //  It's not thanksgiving day yet
            return currentYearStart;
        }
        return reminderForToday;
    }
}