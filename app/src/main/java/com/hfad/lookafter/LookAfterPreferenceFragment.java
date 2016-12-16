package com.hfad.lookafter;


import android.os.Bundle;
import android.preference.PreferenceFragment;

public class LookAfterPreferenceFragment extends PreferenceFragment {


    public LookAfterPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
