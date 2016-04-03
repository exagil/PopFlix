package net.chiragaggarwal.android.popflix.presentation;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.MoviesPreference;

public class SettingsActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        setPreferenceSummaryAsValue(sortOrderPreference());
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String selectedEntry = getSelectedEntryCorrespondingToEntryValue(preference, (String) newValue);
        preference.setSummary(selectedEntry);
        MoviesPreference.getInstance(getApplicationContext()).setRefreshRequired();
        return true;
    }

    private void setPreferenceSummaryAsValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        String preferenceValue = PreferenceManager.getDefaultSharedPreferences(preference.getContext()).getString(preference.getKey(), "");
        onPreferenceChange(preference, preferenceValue);
    }

    private String getSelectedEntryCorrespondingToEntryValue(Preference preference, String newValue) {
        ListPreference listPreference = ((ListPreference) preference);
        CharSequence[] listPreferenceEntries = listPreference.getEntries();
        CharSequence[] listPreferenceEntryValues = listPreference.getEntryValues();

        for (Integer preferenceIndex = 0; preferenceIndex < listPreferenceEntries.length; preferenceIndex++) {
            if (newValue.equals(listPreferenceEntryValues[preferenceIndex]))
                return (String) listPreferenceEntries[preferenceIndex];
        }
        return "";
    }

    private Preference sortOrderPreference() {
        String sortOrderPreferenceKey = getString(R.string.preference_sort_order_key);
        return findPreference(sortOrderPreferenceKey);
    }
}
