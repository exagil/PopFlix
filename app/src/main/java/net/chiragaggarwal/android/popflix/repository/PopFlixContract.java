package net.chiragaggarwal.android.popflix.repository;

import android.content.Context;

import net.chiragaggarwal.android.popflix.R;

public class PopFlixContract {
    private static PopFlixContract instance;
    private final Context context;

    public static PopFlixContract getInstance(Context context) {
        if (instance == null) instance = new PopFlixContract(context);
        return instance;
    }

    private PopFlixContract(Context context) {
        this.context = context;
    }

    public String getDatabaseName() {
        return this.context.getString(R.string.database_name);
    }
}
