package com.example.maventest;

/**
 * 视频 压缩  接口回调
 */

public interface OnProcessListener {

    void onStart();


    void onClipSuccess(String path);



    void onCompressSuccess(String path);


    void onFailed(String msg);


    void onFinish();

}
