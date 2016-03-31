package net.chiragaggarwal.android.popflix.presentation.common;

import android.view.View;
import android.widget.TextView;

import net.chiragaggarwal.android.popflix.R;

public class ReviewViewHolder {
    private View listItemReview;
    private TextView textReviewAuthor;
    private TextView textReviewContent;

    public ReviewViewHolder(View listItemReview) {
        this.listItemReview = listItemReview;
        initializeViews(this.listItemReview);
    }

    private void initializeViews(View listItemReview) {
        this.textReviewAuthor = (TextView) listItemReview.findViewById(R.id.text_review_author);
        this.textReviewContent = (TextView) listItemReview.findViewById(R.id.text_review_content);
    }

    public void bind(String author, String truncatedContent) {
        this.textReviewAuthor.setText(author);
        this.textReviewContent.setText(truncatedContent);
    }
}
