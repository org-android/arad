package com.roc.demo2;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.roc.view.FloatDragView;
import com.roc.view.GlideImageLoader;
import com.roc.view.RichEditor;
import com.ruitu.arad.base.BaseContentActivity;
import com.ruitu.arad.support.widget.progress.ProgressLayout;
import com.ruitu.arad.util.Logg;
import com.ruitu.arad.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseContentActivity {
    private RichEditor richEditor;
    private RelativeLayout rl_font_style;
    private ImageView iv_font;
    private ImageView iv_image;
    private ImageView iv_insert_link;//插入连接
    private ImageView iv_undo;//撤销
    private ImageView iv_redo;//重做

    private ImageView iv_bold;//加粗
    private ImageView iv_italic;//斜体
    private ImageView iv_strikethrough;//删除线
    private ImageView iv_blockquote;//引用
    private ImageView iv_setting;//引用
    private ImageView h1;//h1
    private ImageView h2;//h2
    private ImageView h3;//h3
    private ImageView h4;//h4
    private TextView tv_preview;
    List<ImageView> imageViews;

    private LinearLayout ll;
    private LinearLayout ll_html;
    private RelativeLayout rl_group;
    private FrameLayout frameLayout;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected ProgressLayout findProgressLayout() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        richEditor.setEditorFontSize(18);
        richEditor.setEditorBackgroundColor(Color.BLACK);
        richEditor.setEditorBackgroundColor(Color.WHITE);
        richEditor.setPadding(10, 10, 10, 10);
        richEditor.setPlaceholder("请输入内容...");
        richEditor.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }

    private void initView() {
        richEditor = findViewById(R.id.richEditor);
        tv_preview = findViewById(R.id.tv_preview);
        rl_font_style = findViewById(R.id.rl_font_style);
        iv_font = findViewById(R.id.iv_font);
        iv_image = findViewById(R.id.iv_image);
        iv_insert_link = findViewById(R.id.iv_insert_link);
        iv_undo = findViewById(R.id.iv_undo);
        iv_redo = findViewById(R.id.iv_redo);

        iv_bold = findViewById(R.id.iv_bold);
        iv_italic = findViewById(R.id.iv_italic);
        iv_strikethrough = findViewById(R.id.iv_strikethrough);
        iv_blockquote = findViewById(R.id.iv_blockquote);
        iv_setting = findViewById(R.id.iv_setting);
        frameLayout = findViewById(R.id.frameLayout);
        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        h3 = findViewById(R.id.h3);
        h4 = findViewById(R.id.h4);
        ll = findViewById(R.id.ll);
        ll_html = findViewById(R.id.ll_html);
        rl_group = findViewById(R.id.rl_group);

        iv_font.setOnClickListener(this);
        iv_image.setOnClickListener(this);
        iv_insert_link.setOnClickListener(this);
        iv_undo.setOnClickListener(this);
        iv_redo.setOnClickListener(this);
        tv_preview.setOnClickListener(this);

        iv_bold.setOnClickListener(this);
        iv_italic.setOnClickListener(this);
        iv_strikethrough.setOnClickListener(this);
        iv_blockquote.setOnClickListener(this);
        h1.setOnClickListener(this);
        h2.setOnClickListener(this);
        h3.setOnClickListener(this);
        h4.setOnClickListener(this);
        iv_setting.setOnClickListener(this);
        initImagePicker();

        imageViews = new ArrayList<>();
        imageViews.add(h1);
        imageViews.add(h2);
        imageViews.add(h3);
        imageViews.add(h4);

        FloatDragView.addFloatDragView(this, rl_group, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("点击了悬浮按钮");
            }
        });
    }

    boolean font_check = true;
    boolean bold_check = true;
    boolean italic_check = true;
    boolean blockquote_check = true;
    boolean strikethrough_check = true;
    private final int SYSTEM_GALLERY = 1;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (iv_font == v) {
            if (font_check) {
                rl_font_style.setVisibility(View.VISIBLE);
                iv_font.setSelected(font_check);
                font_check = false;
            } else {
                rl_font_style.setVisibility(View.GONE);
                iv_font.setSelected(font_check);
                font_check = true;
            }
        }

        if (iv_image == v) {
            Intent intent = new Intent(this, ImageGridActivity.class);
            startActivityForResult(intent, 100);

        }

        if (tv_preview == v) {
            Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
            intent.putExtra("diarys", richEditor.getHtml());
            Logg.i(richEditor.getHtml().toString());
            startActivity(intent);
        }

        if (iv_insert_link == v) {
            insertLink();
        }

        if (iv_undo == v) {
            richEditor.undo();
        }

        if (iv_redo == v) {
            richEditor.redo();
        }

        if (iv_bold == v) {
            if (bold_check) {
                iv_bold.setSelected(true);
                bold_check = false;
            } else {
                iv_bold.setSelected(false);
                bold_check = true;
            }
            richEditor.setBold();
        }

        if (iv_italic == v) {
            if (italic_check) {
                iv_italic.setSelected(true);
                italic_check = false;
            } else {
                iv_italic.setSelected(false);
                italic_check = true;
            }
            richEditor.setItalic();
        }

        if (iv_strikethrough == v) {
            if (strikethrough_check) {
                iv_strikethrough.setSelected(true);
                strikethrough_check = false;
            } else {
                iv_strikethrough.setSelected(false);
                strikethrough_check = true;
            }
            richEditor.setStrikeThrough();
        }

        if (iv_blockquote == v) {
            if (blockquote_check) {
                iv_blockquote.setSelected(true);
                blockquote_check = false;
                richEditor.setBlockquote();
            } else {
                iv_blockquote.setSelected(false);
                blockquote_check = true;
                richEditor.setText();
            }

        }

        if (h1 == v) {

            if (h1.isSelected()) {
                h1.setSelected(false);
            } else {
                h1.setSelected(true);
            }
            richEditor.setHeading(1);

        }
        if (h2 == v) {

        }
        if (h3 == v) {

        }
        if (h4 == v) {

        }

        if (iv_setting == v) {
//            if (frameLayout.getVisibility() == View.GONE) {
//                frameLayout.setVisibility(View.VISIBLE);
//            } else {
//                frameLayout.setVisibility(View.GONE);
//            }
            popupWindow();
        }
    }

    public ImageView getSelect() {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i).isSelected()) {
                return imageViews.get(1);
            } else {
                return null;
            }
        }
        return null;
    }

    public void Click(View view) {
        startActivity(LoginActivity.class);
    }


    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(1);              //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            ArrayList<ImageItem> imageItems = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            WindowManager manager = getWindowManager();
            int width = manager.getDefaultDisplay().getWidth();
            int height = manager.getDefaultDisplay().getHeight();
            Logg.i("width: " + width);
            Logg.i("height: " + height);
            Logg.i("Data: " + imageItems.toString());
            richEditor.insertImage("file:" + imageItems.get(0).path, "image", width + "px", height + "px");

        }
    }

    /**
     * 插入链接
     */
    private void insertLink() {
        View rootView = LayoutInflater.from(this).inflate(R.layout.view_common_input_link_view, null);

        final AlertDialog dialog = new AlertDialog.Builder(this, R.style.DialogTheme)
                .setTitle("插入链接")
                .setView(rootView)
                .show();

        final TextInputLayout titleHint = (TextInputLayout) rootView.findViewById(R.id.inputNameHint);
        final TextInputLayout linkHint = (TextInputLayout) rootView.findViewById(R.id.inputHint);
        final EditText title = (EditText) rootView.findViewById(R.id.name);
        final EditText link = (EditText) rootView.findViewById(R.id.text);


        rootView.findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleStr = title.getText().toString().trim();
                String linkStr = link.getText().toString().trim();

                if (TextUtils.isEmpty(titleStr)) {
                    titleHint.setError("不能为空");
                    return;
                }
                if (TextUtils.isEmpty(linkStr)) {
                    linkHint.setError("不能为空");
                    return;
                }

                if (titleHint.isErrorEnabled())
                    titleHint.setErrorEnabled(false);
                if (linkHint.isErrorEnabled())
                    linkHint.setErrorEnabled(false);

//            mEditorFragment.getPerformEditable().perform(R.id.id_shortcut_insert_link, titleStr, linkStr);
                richEditor.insertLink(linkStr, titleStr);
                dialog.dismiss();
            }
        });

//        rootView.findViewById(R.id.cancel).setOnClickListener(v -> {
//            dialog.dismiss();
//        });

        dialog.show();
    }

    PopupWindow popupWindow;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void popupWindow() {
        View inflate = LayoutInflater.from(this).inflate(R.layout.popupwindow_setting, null);
        popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int popupWidth = inflate.getMeasuredWidth();    //  获取测量后的宽度
        int popupHeight = inflate.getMeasuredHeight();  //获取测量后的高度
        int[] location = new int[2];
// 允许点击外部消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        popupWindow.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        popupWindow.setFocusable(true);
// 获得位置 这里的v是目标控件，就是你要放在这个v的上面还是下面
        ll.getLocationOnScreen(location);
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);  //设置动画
//这里就可自定义在上方和下方了 ，这种方式是为了确定在某个位置，某个控件的左边，右边，上边，下边都可以
        popupWindow.showAtLocation(ll_html, Gravity.BOTTOM, (location[0] + ll.getWidth() / 2) - popupWidth / 2, ll_html.getHeight());

    }
}
