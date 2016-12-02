package com.hfad.lookafter.notifications;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.hfad.lookafter.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NotificationActivity extends Activity {

    @BindView(R.id.timePicker)
    TimePicker timePicker;
    @BindView(R.id.datePicker)
    DatePicker datePicker;

    private Calendar calendar;
    private int year;
    private int month;
    private int dayOfMonth;
    private int hourOfDay;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //TODO: przypomnienia

        ButterKnife.bind(this);

        initializeCalendar();

    }

    private void initializeCalendar() {
        calendar = Calendar.getInstance();

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            }
        };
    }

    private void setAlarm() {
        Intent intent = new Intent(NotificationActivity.this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(NotificationActivity.this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @OnClick(R.id.notification_button)
    public void onNotificationButtonClick() {

        Snackbar.make(findViewById(R.id.notification_button), R.string.notification_added, Snackbar.LENGTH_SHORT)
                .setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        super.onDismissed(snackbar, event);

                        finish();
                    }
                }).show();


        setAlarm();

    }


}