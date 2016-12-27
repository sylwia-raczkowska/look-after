package com.hfad.lookafter.settings;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import com.hfad.lookafter.R;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String STYLE_KEY = String.valueOf(R.string.style_preference);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LookAfterPreferenceFragment())
                .commit();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
//
//    if (key.equals(STYLE_KEY)){
//        Preference stylePref = findPreference(key);
//        stylePref.setSummary(sharedPreferences.getString(key))
//    }
    }

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

}
