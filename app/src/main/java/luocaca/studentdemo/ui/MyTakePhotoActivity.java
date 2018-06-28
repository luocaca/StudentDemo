package luocaca.studentdemo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import luocaca.studentdemo.http.UploadRunnable;
import luocaca.studentdemo.i.OnImageUploadListener;
import ndk.darren.com.takephoto.TakePhotoActivity;

/**
 * selectList
 */

public class MyTakePhotoActivity extends TakePhotoActivity {


    private ExecutorService executorService;

    public static void start(Activity mActivity) {
        mActivity.startActivity(new Intent(mActivity, MyTakePhotoActivity.class));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        executorService = Executors.newFixedThreadPool(10);

    }

    @Override
    public void 上传图片(List<LocalMedia> selectList) {


        Toast.makeText(this, "" + selectList.size() + "张图片", Toast.LENGTH_SHORT).show();


        for (LocalMedia localMedia : selectList) {
                   executorService.execute(new UploadRunnable(new OnImageUploadListener() {
                @Override
                public void onSucceed(String json) {

                    Log.i("onSucceed", json);
                }

                @Override
                public void onFailed(String msg) {
                    Log.i("onFailed", msg);
                }
            }, new File(localMedia.getCompressPath()), this));

        }


        /**
         *   executorService.execute(new UploadRunnable(new OnImageUploadListener() {
        @Override public void onSucceed(String json) {
        Log.i(TAG, "onSucceed" + json);

        et_detail.post(new Runnable() {
        @Override public void run() {
        et_detail.append("\n" + json);
        }
        });
        }

        @Override public void onFailed(String msg) {
        Log.i(TAG, "onFailed" + msg);
        }
        }, file, mActivity));
         */


    }
}
