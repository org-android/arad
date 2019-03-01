package com.roc.view.adapter;

import android.widget.BaseAdapter;

import com.roc.view.EditSpinnerFilter;

public abstract class EditSpinnerAdapter extends BaseAdapter {
    /**
     * editText输入监听
     *
     * @return
     */
    public abstract EditSpinnerFilter getEditSpinnerFilter();

    /**
     * 获取需要填入editText的字符串
     * @param position
     * @return
     */
    public abstract String getItemString(int position);
}
