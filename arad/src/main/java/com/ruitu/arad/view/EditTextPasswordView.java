//package com.ruitu.arad.view;
//
//import android.content.Context;
//import android.graphics.drawable.Drawable;
//import android.text.InputType;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//
//import com.ruitu.arad.R;
//
//public class EditTextPasswordView extends EditText {
//    private Context mContext;
//
//
//    public EditTextPasswordView(Context context) {
//        super(context);
//    }
//
//    public EditTextPasswordView(Context context, AttributeSet attrs) {
//        super(context, attrs, android.R.attr.editTextStyle);
//        this.mContext = context;
//        init();
//
//    }
//
//    public EditTextPasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }
//private ImageView iv_password;
//private EditText et_password;
//    private void init() {
//        View view = inflate(mContext,R.layout.item_isvisible_password,null);
//        iv
//    }
//
//    /**
//     * 点击密码是否可见
//     *
//     * @param isTouch
//     */
//    private void setPasswordVisible(boolean isTouch) {
//        if (isTouch) {
//            setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        } else
//            setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//    }
//}
