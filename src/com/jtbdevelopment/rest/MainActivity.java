package com.jtbdevelopment.rest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.jtbdevelopment.rest.helpers.NextReminderCalculator;
import org.joda.time.LocalDateTime;

public class MainActivity extends Activity {
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

        LocalDateTime nextReminder = NextReminderCalculator.getNextDailyReminderTime();
        TextView nextReminderText = (TextView) findViewById(R.id.reminderTime);
        nextReminderText.setText(nextReminder.toString("dd MMM yyyy HH:mm:ss"));
    }
}
