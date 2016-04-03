package net.chiragaggarwal.android.popflix.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import net.chiragaggarwal.android.popflix.R;

public class MoviesPreference {
    private Context applicationContext;
    private static MoviesPreference moviesPreference;

    public static MoviesPreference getInstance(Context applicationContext) {
        if (moviesPreference == null)
            moviesPreference = new MoviesPreference(applicationContext);
        return moviesPreference;
    }

    private MoviesPreference(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public boolean isRefreshRequired() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        boolean isRefreshRequiredCurrently = sharedPreferences.getBoolean(isRefreshRequiredKey(), false);
        if (isRefreshRequiredCurrently) {
            setRefreshNotRequired();
            return true;
        } else {
            return false;
        }
    }

    public boolean setRefreshRequired() {
        return refreshRequired(applicationContext, true);
    }

    @NonNull
    private String isRefreshRequiredKey() {
        return applicationContext.getString(R.string.is_refresh_required_key);
    }

    private boolean setRefreshNotRequired() {
        return refreshRequired(applicationContext, false);
    }

    private boolean refreshRequired(Context applicationContext, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(isRefreshRequiredKey(), value);
        return sharedPreferencesEditor.commit();
    }
}
