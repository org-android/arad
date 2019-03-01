package com.roc.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.text.Annotation;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;

import com.roc.demo2.R;
import com.roc.view.adapter.EditSpinnerAdapter;
import com.roc.view.adapter.SimpleAdapter;

import java.util.List;

public class EditSpinner extends RelativeLayout implements View.OnClickListener , AdapterView.OnItemClickListener, TextWatcher {
    private EditText editText;
    private ImageView ivRight;
    private View mRightImageTopView;
    private Context mContext;
    private ListPopupWindow listPopupWindow;
    private long popupWindowHideTime;
    EditSpinnerAdapter adapter;
    private Annotation mAnimation;
    private Annotation mResetAnimation;
    private AdapterView.OnItemClickListener onItemClickListener;
    private int maxKin = 1;
    public EditSpinner(Context context) {
        super(context);

    }

    public EditSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(attrs);
    }

    public EditSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(AttributeSet attrs) {
        LayoutInflater.from(mContext).inflate(R.layout.edit_spinner,this);
        editText = findViewById(R.id.et_spinner_edit);
        ivRight = findViewById(R.id.iv_spinner_expand);
        mRightImageTopView = findViewById(R.id.edit_spinner_expand_above);
    }

    public void setItemData(List<String> data) {
        adapter = new SimpleAdapter(mContext, data);
        setAdapter(adapter);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setText(String text) {
        editText.setText(text);
    }

    public void setTextColor(@ColorInt int color) {
        editText.setTextColor(color);
    }

    public String getText() {
        return editText.getText().toString();
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }

    public void setRightImageDrawable(Drawable drawable) {
        ivRight.setImageDrawable(drawable);
    }

    public void setRightImageResource(@DrawableRes int res) {
        ivRight.setImageResource(res);
    }

    public void setAdapter(EditSpinnerAdapter adapter) {
        this.adapter = adapter;
//        setBaseAdapter(this.adapter);
    }

    public void setMaxLine(int maxLine) {
        this.maxKin = maxLine;
        editText.setMaxLines(this.maxKin);
    }

    private void initAnimation() {
//        mAnimation = new RotateAnimation(0, -90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mAnimation.setDuration(300);
//        mAnimation.setFillAfter(true);
//        mResetAnimation = new RotateAnimation(-90, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        mResetAnimation.setDuration(300);
//        mResetAnimation.setFillAfter(true);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
