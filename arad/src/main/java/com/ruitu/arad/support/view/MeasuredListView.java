package com.ruitu.arad.support.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 嵌入到scrollView中,重新计算高度的ListView
 */
public class MeasuredListView extends ListView {

    public MeasuredListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasuredListView(Context context) {
        super(context);
    }

    public MeasuredListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}