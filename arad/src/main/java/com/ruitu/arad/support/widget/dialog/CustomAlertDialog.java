package com.ruitu.arad.support.widget.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ruitu.arad.R;

/**
 * Created by  on 2018/6/11.
 */
public class CustomAlertDialog extends AlertDialog {
    private Context mContext;
    private int layoutId;
    private OkOnClickListener okOnClickListener;
    private String title;

    public CustomAlertDialog(@NonNull Context context, String title, OkOnClickListener okOnClickListener) {
        super(context);
        this.mContext = context;
        this.title = title;
        this.okOnClickListener = okOnClickListener;
        initView();
    }

    protected CustomAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        initView();
    }

    protected CustomAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.custom_dialog, null);
        TextView cancel_tv = view.findViewById(R.id.cancel_tv);
        TextView dialog_title_tv = view.findViewById(R.id.dialog_title_tv);
        TextView ok_tv = view.findViewById(R.id.ok_tv);
        setView(view);
        dialog_title_tv.setText(title);
        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okOnClickListener.OkOnClick();
            }
        });
    }

    public void setOkOnClickListener(OkOnClickListener okOnClickListener) {
        this.okOnClickListener = okOnClickListener;
    }

    public interface OkOnClickListener {
        void OkOnClick();
    }
}
