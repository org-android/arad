package com.roc.view;

import android.app.Activity;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.roc.Utils;
import com.roc.demo2.R;

public class FloatDragView {
    private Activity mActivity;
    private ImageView mImageView;
    private static int mScreenWidth = -1;//屏幕的宽度
    private static int mScreanHeight = -1;//
    private int relativeMoveX;//空间相对品谷左上角移动的位置X
    private int relativeMoveY;
    private boolean isIntercept = false;//是否截断touch事件
    private int startDownX;//按下时的位置控件相对屏幕左上角的位置X
    private int startDownY;//按下时的位置控件相对屏幕左上角的位置Y
    private static int[] lastPosition;//用于记录上一次的位置(坐标0对应X,坐标1对应Y)

    /**
     * @param mActivity
     * @param relativeLayout
     * @param clickListener
     */
    public FloatDragView(Activity mActivity, RelativeLayout relativeLayout, View.OnClickListener clickListener) {
        this.mActivity = mActivity;
        FloatDragView floatDragView = new FloatDragView(mActivity);
        ImageView imageView = floatDragView.getFloatDragView(clickListener);
        relativeLayout.addView(imageView);
    }

    public FloatDragView(Activity mActivity) {
        setScreenHW(mActivity);
        this.mActivity = mActivity;
        lastPosition = new int[]{0, 0};
    }

    public static ImageView addFloatDragView(Activity activity, RelativeLayout relativeLayout,
                                             View.OnClickListener clickListener) {
        FloatDragView floatDragView = new FloatDragView(activity);
        ImageView imageView = floatDragView.getFloatDragView(clickListener);
        relativeLayout.addView(imageView);
        return imageView;
    }

    private ImageView getFloatDragView(View.OnClickListener onClickListener) {
        if (mImageView != null) {
            return mImageView;
        } else {
            mImageView = new ImageView(mActivity);
            mImageView.setClickable(true);
            mImageView.setFocusable(true);
            mImageView.setImageResource(R.drawable.zhuce);
            setFloatDragViewParams(mImageView);
            mImageView.setOnClickListener(onClickListener);
            setFloatDragViewTouch(mImageView);
        }
        return mImageView;
    }

    //可拖动按钮的touch事件
    private void setFloatDragViewTouch(ImageView mImageView) {
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {

                    case MotionEvent.ACTION_DOWN:
                        isIntercept = false;
                        startDownX = relativeMoveX = (int) event.getRawX();
                        startDownY = relativeMoveY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (event.getRawX()) - relativeMoveX;
                        int dy = (int) (event.getRawY()) - relativeMoveY;

                        int left = v.getLeft() + dx;
                        int top = v.getTop() + dy;
                        int right = v.getRight() + dx;
                        int bottom = v.getBottom() + dy;

                        if (left < 0) {
                            left = 0;
                            right = left + v.getWidth();
                        }

                        if (right > mScreenWidth) {
                            right = mScreenWidth;
                            left = right - v.getWidth();
                        }

                        if (top < 0) {
                            top = 0;
                            bottom = top + v.getHeight();
                        }

                        if (bottom > mScreanHeight) {
                            bottom = mScreanHeight;
                            top = bottom - v.getHeight();
                        }

                        v.layout(left, top, right, bottom);
                        relativeMoveX = (int) event.getRawX();
                        relativeMoveY = (int) event.getRawY();
                        break;

                    case MotionEvent.ACTION_UP:
                        int lastMoveDx = Math.abs((int) event.getRawX() - startDownX);
                        int lastMoveDy = Math.abs((int) event.getRawY() - startDownY);

                        if (5 < lastMoveDx || 5 < lastMoveDy) {
                            isIntercept = true;
                        } else {
                            isIntercept = false;
                        }

                        //每次移动都要设置其layout,不然由于父布局可能嵌套listview
                        //当父布局发生改变冲毁(如下拉刷新时)则移动的view会回到原来的位置
                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(v.getLeft(), v.getTop(), 0, 0);
                        v.setLayoutParams(layoutParams);
                        setImageViewNearEdge(v);
                        break;
                }
                return isIntercept;
            }

        });
    }

    private void setImageViewNearEdge(final View v) {
        if (v.getLeft() < ((Utils.getScreenSize(mActivity).x) / 2)){
            TranslateAnimation animation = new TranslateAnimation(0, -v.getLeft(), 0, 0);
            animation.setDuration(300);//动画持续时间
            animation.setRepeatCount(0);//设置重复次数
            animation.setFillAfter(true);
            animation.setRepeatMode(Animation.ABSOLUTE);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.clearAnimation();
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0, v.getTop(), 0, 0);
                    v.setLayoutParams(layoutParams);
                    v.postInvalidateOnAnimation();
                    lastPosition[0] = 0;
                    lastPosition[1] = v.getTop();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {


                }
            });
            v.setAnimation(animation);
        } else {
            final TranslateAnimation animation = new TranslateAnimation(0, (Utils.getScreenSize(mActivity).x
                    - v.getLeft() - v.getWidth()), 0, 0);
            animation.setDuration(300);
            animation.setRepeatCount(0);
            animation.setRepeatMode(Animation.ABSOLUTE);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.clearAnimation();
                    RelativeLayout.LayoutParams lpFeedback = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lpFeedback.setMargins(Utils.getScreenSize(mActivity).x - v.getWidth(), v.getTop(), 0, 0);
                    v.setLayoutParams(lpFeedback);
                    v.postInvalidateOnAnimation();
                    lastPosition[0] = Utils.getScreenSize(mActivity).x - v.getWidth();
                    lastPosition[1] = v.getTop();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            v.setAnimation(animation);
        }
    }

    private void setFloatDragViewParams(ImageView mImageView) {
        //记录最后图片在屏幕的位置
        int moveX = lastPosition[0];
        int moveY = lastPosition[1];
        if (0 != moveX || 0 != moveY) {

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(moveX, moveY, 0, 0);
            mImageView.setLayoutParams(layoutParams);
        } else {
            //初始位置
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 20, 218);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            mImageView.setLayoutParams(layoutParams);
        }
    }

    // 计算屏幕的实际高宽
    private void setScreenHW(Activity context) {
        if (mScreanHeight < 0) {
            // 减去状态栏高度，否则挨着底部移动，导致图标变小
            Point screen = Utils.getScreenSize(context);
            mScreenWidth = screen.x;
            mScreanHeight = screen.y - Utils.getStatusBarHeight(context);
        }
    }


}
