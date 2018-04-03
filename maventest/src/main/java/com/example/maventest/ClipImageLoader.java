package com.example.maventest;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * 图片加载 接口  通过接口来 快速修改  图片加载框架
 */

public interface ClipImageLoader {

    void display( String url , ImageView view , float rotate );

    ClipImageLoader DEFAULT_IMAGE_LOADER = new ClipImageLoader() {
        @Override
        public void display(String url, ImageView view,float rotate) {
            Glide.with(view.getContext())
                    .load(url).
                    transform(new RotateTransformation(view.getContext(), rotate))
                    .into(view);
        }
    };


}
