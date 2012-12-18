package com.jtbdevelopment.rest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import com.jtbdevelopment.rest.helpers.AlarmType;
import com.jtbdevelopment.rest.helpers.NextReminderCalculator;

/**
 * Date: 12/17/12
 * Time: 10:02 PM
 */
public class PreferencesActivity extends PreferenceActivity {
    private SharedPreferences preferences = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        preferences.unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        super.onPause();
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String s) {
            RESTReceiver.cancelAlarms(PreferencesActivity.this);
            RESTReceiver.setAnAlarm(PreferencesActivity.this, AlarmType.ALARM, NextReminderCalculator.getNextDailyReminderTime(PreferencesActivity.this));
        }
    };
}