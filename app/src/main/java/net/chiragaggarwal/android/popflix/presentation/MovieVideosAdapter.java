package net.chiragaggarwal.android.popflix.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Video;
import net.chiragaggarwal.android.popflix.models.Videos;

public class MovieVideosAdapter extends BaseAdapter {
    private Videos videos;
    private Context context;

    public MovieVideosAdapter(Videos videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.videos.count();
    }

    @Override
    public Video getItem(int position) {
        return this.videos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Video video = getItem(position);
        VideoViewHolder videoViewHolder;
        View listItemVideo;
        if (convertView == null) {
            listItemVideo = LayoutInflater.from(this.context).inflate(R.layout.list_item_video, parent, false);
            videoViewHolder = new VideoViewHolder(listItemVideo);
            videoViewHolder.initialize();
            listItemVideo.setTag(videoViewHolder);
        } else {
            listItemVideo = convertView;
            videoViewHolder = (VideoViewHolder) convertView.getTag();
        }
        videoViewHolder.bind(video.formattedName());
        return listItemVideo;
    }
}
