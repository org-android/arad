package com.roc.view;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.roc.demo2.R;

import java.io.File;

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        /*Picasso.with(activity)                            //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.drawable.bg_stack_blur_default)           //设置错误图片
                .placeholder(R.drawable.bg_stack_blur_default)     //设置占位图片
                .into(imageView);*/

        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .into(imageView);
    }

    //在这里注意用glide加载图片的话一定要在下面这个方法 里面也配置上，否者会出现预览图片还有不显示图片
    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .into(imageView);
    }


    @Override
    public void clearMemoryCache() {}
    //这里是清除缓存的方法,根据需要自己实现

}
