package com.jtbdevelopment.rest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jtbdevelopment.rest.helpers.NextReminderCalculator;
import org.joda.time.LocalDateTime;

public class MainActivity extends Activity {
    private TextView nextReminderTime;
    private TextView nextReminderText;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (savedInstanceState == null) {
            BootReceiver.kickOffReminderService(this);
        }

        nextReminderTime = (TextView) findViewById(R.id.reminderTime);
        nextReminderText = (TextView) findViewById(R.id.reminderText);
        updateReminderDetails();

        Button button = (Button) findViewById(R.id.preferenceButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PreferencesActivity.class);
                MainActivity.this.startActivity(intent);
                PreferenceManager.getDefaultSharedPreferences(MainActivity.this).registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
            }
        });
    }

    private void updateReminderDetails() {
        LocalDateTime nextReminder = NextReminderCalculator.getNextDailyReminderTime(this);
        nextReminderTime.setText(nextReminder.toString("dd MMM yyyy HH:mm:ss"));
        nextReminderText.setText(PreferenceManager.getDefaultSharedPreferences(this).getString("alarmText", getString(R.string.defaultAlarmText)));
    }

    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String s) {
            updateReminderDetails();
        }
    };
}
