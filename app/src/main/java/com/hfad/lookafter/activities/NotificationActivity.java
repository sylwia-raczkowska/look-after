package com.hfad.lookafter.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.hfad.lookafter.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends Activity {

    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //TODO: przypomnienia

        ButterKnife.bind(this);


    }

    @OnClick(R.id.notification_button)
    public void onNotificationButtonClick() {

        NotificationManager manager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("New notification")
                .setContentText("Content text")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();

        manager.notify(0, notification);

    }

    @Override
    protected void onStart() {
        super.onStart();

        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (receiver != null)
            unregisterReceiver(receiver);
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }
}
