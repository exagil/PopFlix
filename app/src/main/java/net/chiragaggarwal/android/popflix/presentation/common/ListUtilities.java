package net.chiragaggarwal.android.popflix.presentation.common;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

// ListUtilities performs any UI related modifications for a ListView

public class ListUtilities {
    private ListView listView;

    public ListUtilities(ListView listView) {
        this.listView = listView;
    }

    public void setHeightToSumOfHeightsOfElements() {
        ListAdapter adapter = this.listView.getAdapter();
        if (adapter == null) return;
        int totalHeight = 0;
        for (int elementIndex = 0; elementIndex < adapter.getCount(); elementIndex++) {
            View listItem = adapter.getView(elementIndex, null, this.listView);
            listItem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams listLayoutParams = this.listView.getLayoutParams();
        listLayoutParams.height = totalHeight;
    }
}
