package com.ruitu.arad.base.base_list;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.jdsjlzx.interfaces.IRefreshHeader;
import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.WeakHandler;
import com.github.jdsjlzx.view.SimpleViewSwitcher;
import com.ruitu.arad.R;
import com.ruitu.arad.util.SizeUtils;

import java.util.Date;

public class CustomRefreshHeader extends LinearLayout implements IRefreshHeader {
    private LinearLayout mContainer;
    private ImageView mArrowImageView;
    private SimpleViewSwitcher mProgressBar;
    private TextView mStatusTextView;
    private TextView mHeaderTimeView;

    private Animation mRotateUpAnim;
    private Animation mRotateDownAnim;

    private static final int ROTATE_ANIM_DURATION = 180;

    public int mMeasuredHeight;
    private int hintColor;
    private int mState = STATE_NORMAL;

    private WeakHandler mHandler = new WeakHandler();

    public CustomRefreshHeader(Context context) {
        super(context);
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public CustomRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        // 初始情况，设置下拉刷新view高度为0
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(com.github.jdsjlzx.R.layout.layout_recyclerview_refresh_header, null);
        mContainer.setBackgroundColor(Color.parseColor("#00000000"));

        addView(mContainer, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);

        mArrowImageView = (ImageView) findViewById(com.github.jdsjlzx.R.id.listview_header_arrow);
        mArrowImageView.setImageResource(R.drawable.icon_loading_1);
        RelativeLayout.LayoutParams params0 = (RelativeLayout.LayoutParams) mArrowImageView.getLayoutParams();
        params0.width = SizeUtils.dp2px(40f);
        params0.height = SizeUtils.dp2px(40f);
        mArrowImageView.setLayoutParams(params0);
        mStatusTextView = (TextView) findViewById(com.github.jdsjlzx.R.id.refresh_status_textview);

        //init the progress view
        mProgressBar = (SimpleViewSwitcher) findViewById(com.github.jdsjlzx.R.id.listview_header_progressbar);
//        mProgressBar.setView(initIndicatorView(ProgressStyle.BallSpinFadeLoader));

        ImageView imageView = new ImageView(getContext());//40.0*35.2
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        ViewGroup.LayoutParams params = new LayoutParams(SizeUtils.dp2px(40f), SizeUtils.dp2px(40f));
        imageView.setLayoutParams(params);
        imageView.setBackgroundResource(R.drawable.spinner);
        AnimationDrawable drawable = (AnimationDrawable) imageView.getBackground();
        drawable.start();

        mProgressBar.setView(imageView);

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);

        mHeaderTimeView = (TextView) findViewById(com.github.jdsjlzx.R.id.last_refresh_time);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
        hintColor = android.R.color.darker_gray;
    }

    public void setProgressStyle(int style) {
        if (style == ProgressStyle.SysProgress) {
            ProgressBar progressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
            mProgressBar.setView(progressBar);
        } else {
            mProgressBar.setView(initIndicatorView(style));
        }
    }

    private View initIndicatorView(int style) {
        AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) LayoutInflater.from(getContext()).inflate(com.github.jdsjlzx.R.layout.layout_indicator_view, null);
        progressView.setIndicatorId(style);
        progressView.setIndicatorColor(Color.GRAY);
        return progressView;
    }

    public void setIndicatorColor(int color) {
        if (mProgressBar.getChildAt(0) instanceof AVLoadingIndicatorView) {
            AVLoadingIndicatorView progressView = (AVLoadingIndicatorView) mProgressBar.getChildAt(0);
            progressView.setIndicatorColor(color);
        }
    }

    public void setHintTextColor(int color) {
        this.hintColor = color;
        //下面的代码是自己加的(2018.08.22)
        mStatusTextView.setTextColor(ContextCompat.getColor(getContext(), hintColor));
    }

    public void setViewBackgroundColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setArrowImageView(int resid) {
        mArrowImageView.setImageResource(resid);
    }

    public void setState(int state) {
        if (state == mState) return;

        if (state == STATE_REFRESHING) {    // 显示进度
            mArrowImageView.clearAnimation();
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            smoothScrollTo(mMeasuredHeight);
        } else if (state == STATE_DONE) {
            mArrowImageView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        } else {    // 显示箭头图片
            mArrowImageView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }

        mStatusTextView.setTextColor(ContextCompat.getColor(getContext(), hintColor));

        switch (state) {
            case STATE_NORMAL:
                if (mState == STATE_RELEASE_TO_REFRESH) {
//                    mArrowImageView.startAnimation(mRotateDownAnim);
                }
                if (mState == STATE_REFRESHING) {
                    mArrowImageView.clearAnimation();
                }
                mStatusTextView.setText(com.github.jdsjlzx.R.string.listview_header_hint_normal);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mArrowImageView.clearAnimation();
//                    mArrowImageView.startAnimation(mRotateUpAnim);
                    mStatusTextView.setText(com.github.jdsjlzx.R.string.listview_header_hint_release);
                }
                break;
            case STATE_REFRESHING:
                mStatusTextView.setText(com.github.jdsjlzx.R.string.refreshing);
                break;
            case STATE_DONE:
                mStatusTextView.setText(com.github.jdsjlzx.R.string.refresh_done);
                break;
            default:
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete() {
        mHeaderTimeView.setText(friendlyTime(new Date()));
        setState(STATE_DONE);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 200);
    }

    @Override
    public View getHeaderView() {
        return this;
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    //垂直滑动时该方法不实现
    @Override
    public int getVisibleWidth() {
        return 0;
    }

    @Override
    public void onReset() {
        setState(STATE_NORMAL);
    }

    @Override
    public void onPrepare() {
        setState(STATE_RELEASE_TO_REFRESH);
    }

    @Override
    public void onRefreshing() {
        setState(STATE_REFRESHING);
    }

    @Override
    public void onMove(float offSet, float sumOffSet) {
        if (null != onPullListener) {
            onPullListener.onPull(offSet, sumOffSet);
        }
        if (getVisibleHeight() > 0 || offSet > 0) {
            setVisibleHeight((int) offSet + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    onPrepare();
                } else {
                    onReset();
                }
            }
        }
    }

    public void setOnPullListener(OnPullListener onPullListener) {
        this.onPullListener = onPullListener;
    }

    private OnPullListener onPullListener;

    public interface OnPullListener {
        void onPull(float offSet, float sumOffSet);//开始拉动

        void onReset();//重置
    }

    @Override
    public boolean onRelease() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) {// not visible.
            isOnRefresh = false;
        }

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height > mMeasuredHeight) {
            smoothScrollTo(mMeasuredHeight);
        }
        if (mState != STATE_REFRESHING) {
            smoothScrollTo(0);

            if (null != onPullListener) {
                onPullListener.onReset();
            }
        }

        if (mState == STATE_REFRESHING) {
            int destHeight = mMeasuredHeight;
            smoothScrollTo(destHeight);
        }

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        mHandler.postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);

        if (null != onPullListener) {
            onPullListener.onReset();
        }
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public String friendlyTime(Date time) {
        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);

        if (ct == 0) {
            return getContext().getResources().getString(com.github.jdsjlzx.R.string.text_just);
        }

        if (ct > 0 && ct < 60) {
            return ct + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_seconds_ago);
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_minute_ago);
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_hour_ago);
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_day_ago);
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_month_ago);
        }
        return ct / 31104000 + getContext().getResources().getString(com.github.jdsjlzx.R.string.text_year_ago);
    }
}
