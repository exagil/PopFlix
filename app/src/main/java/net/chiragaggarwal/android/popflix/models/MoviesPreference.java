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

    public boolean hasSortOrderChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        boolean hasSortOrderChangedCurrently = sharedPreferences.getBoolean(sortOrderChangedKey(), false);
        if (hasSortOrderChangedCurrently) {
            setSortOrderAsUnchanged();
            return true;
        } else {
            return false;
        }
    }

    public boolean setSortOrderAsChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(sortOrderChangedKey(), true);
        return sharedPreferencesEditor.commit();
    }

    @NonNull
    private String sortOrderChangedKey() {
        return applicationContext.getString(R.string.has_sort_order_changed);
    }

    private boolean setSortOrderAsUnchanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putBoolean(sortOrderChangedKey(), false);
        return sharedPreferencesEditor.commit();
    }
}
