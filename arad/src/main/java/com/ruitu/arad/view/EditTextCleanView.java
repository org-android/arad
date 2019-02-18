package com.ruitu.arad.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.ruitu.arad.R;


/**
 * Created by  on 2018/5/4.
 */
public class EditTextCleanView extends EditText implements View.OnFocusChangeListener,TextWatcher {
    /**
     * 清除按钮的引用
     */
    private Drawable drawable;

    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    public EditTextCleanView(Context context) {
        super(context);
    }

    public EditTextCleanView(Context context, AttributeSet attrs) {
        super(context, attrs, android.R.attr.editTextStyle);
        init();

    }

    public EditTextCleanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {
        drawable = getCompoundDrawables()[2];
        if (drawable == null) {
            drawable = getResources().getDrawable(R.mipmap.icon_cleaner);
        }

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setCleanIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                boolean touchble = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < (getWidth() - getPaddingRight()));
                if (touchble) {
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * @param b
     */
    private void setCleanIconVisible(boolean b) {
        Drawable right = b ? drawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        this.hasFoucs = b;
        if (hasFoucs) {
            setCleanIconVisible(getText().length() > 0);
        } else {
            setCleanIconVisible(false);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (hasFoucs) {
            setCleanIconVisible(charSequence.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.startAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
