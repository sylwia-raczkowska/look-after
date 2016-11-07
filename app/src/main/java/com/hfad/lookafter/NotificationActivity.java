package com.hfad.lookafter;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;

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
