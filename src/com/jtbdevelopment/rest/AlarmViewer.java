package com.jtbdevelopment.rest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.jtbdevelopment.rest.helpers.AlarmType;
import org.joda.time.LocalDateTime;

/**
 * Date: 12/2/12
 * Time: 12:53 PM
 */
public class AlarmViewer extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmviewer);
        Button done = (Button) findViewById(R.id.doneButton);
        Button moreTime = (Button) findViewById(R.id.remindMeMore);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlarmViewer.this.finish();
            }
        });

        moreTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //  TODO - make configurable
                RESTReceiver.setAnAlarm(AlarmViewer.this, AlarmType.SNOOZE, new LocalDateTime().plusMinutes(30));
                AlarmViewer.this.finish();
            }
        });
    }
}