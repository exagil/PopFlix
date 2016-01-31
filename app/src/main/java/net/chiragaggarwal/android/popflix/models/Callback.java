package net.chiragaggarwal.android.popflix.models;

public interface Callback<T> {
    void onSuccess(T t);

    void onFailure();
}
