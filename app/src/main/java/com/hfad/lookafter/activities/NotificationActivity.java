package com.hfad.lookafter.activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;

import com.hfad.lookafter.R;

public class NotificationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //TODO: przypomnienia
    }

}
