package com.ruitu.router_module.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruitu.arad.Arad;

public class GlideUtil {
    public static void loadImg(String url, ImageView iv) {
        try {
            Glide.with(Arad.app).load(url).apply(Arad.opts).into(iv);
        } catch (Exception e) {

        }
    }
}
