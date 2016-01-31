package net.chiragaggarwal.android.popflix.models;

public interface Callback<T, E> {
    void onSuccess(T t);

    void onFailure(E e);

    void onUnexpectedFailure();
}
