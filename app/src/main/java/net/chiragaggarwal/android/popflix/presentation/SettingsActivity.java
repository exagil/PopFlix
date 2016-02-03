package net.chiragaggarwal.android.popflix.presentation;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import net.chiragaggarwal.android.popflix.R;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
