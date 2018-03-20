package luocaca.studentdemo.http;

import android.app.Activity;

import java.io.File;

import luocaca.studentdemo.i.OnImageUploadListener;

/**
 * 图片上传线程
 */

public class UploadRunnable implements Runnable {

    private OnImageUploadListener mUploadListener;
    private File mFile;
    private Activity mActivity;

    public UploadRunnable(OnImageUploadListener uploadListener, File url, Activity activity) {
        mUploadListener = uploadListener;
        mFile = url;
        mActivity = activity;
    }

    @Override
    public void run() {
        if (mUploadListener != null) {
            mUploadListener.onSucceed(UpLoadUtil.testUploadImage(mFile.getAbsolutePath(), UpLoadUtil.hostLocal, mActivity));
        }
    }


}
