package net.chiragaggarwal.android.popflix;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class PopFlixApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
        picassoBuilder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso picassoInstance = picassoBuilder.build();
        picassoInstance.setIndicatorsEnabled(true);
        Picasso.setSingletonInstance(picassoInstance);
    }
}
