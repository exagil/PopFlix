package net.chiragaggarwal.android.popflix.presentation.common;

import android.view.View;
import android.widget.TextView;

import net.chiragaggarwal.android.popflix.R;

public class VideoViewHolder {
    private View listItemVideo;
    private TextView textVideoName;

    public VideoViewHolder(View listItemVideo) {
        this.listItemVideo = listItemVideo;
    }

    public void initialize() {
        this.textVideoName = (TextView) this.listItemVideo.findViewById(R.id.text_video_name);
    }

    public void bind(String formattedName) {
        this.textVideoName.setText(formattedName);
    }
}
