package net.chiragaggarwal.android.popflix.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Review;
import net.chiragaggarwal.android.popflix.models.Reviews;

public class MovieReviewsAdapter extends BaseAdapter {
    private Reviews reviews;
    private Context context;

    public MovieReviewsAdapter(Reviews reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reviews.count();
    }

    @Override
    public Review getItem(int position) {
        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemReview;
        ReviewViewHolder reviewViewHolder;
        Review review = getItem(position);

        if (convertView == null) {
            listItemReview = LayoutInflater.from(context).inflate(R.layout.list_item_review, parent, false);
            reviewViewHolder = new ReviewViewHolder(listItemReview);
            listItemReview.setTag(reviewViewHolder);
        } else {
            listItemReview = convertView;
            reviewViewHolder = ((ReviewViewHolder) listItemReview.getTag());
        }

        reviewViewHolder.bind(review.author, review.content);
        return listItemReview;
    }
}
