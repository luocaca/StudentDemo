package ndk.darren.com.takephoto;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class TakePhotoActivity extends Activity implements View.OnClickListener {


    public static final int TAKE_PHOTO = 1;

    public static final int CHOOSE_PHOTO = 2;

    public static final int CROP_PHOTO = 3;

    private ImageView take;
    private ImageView clip;

    private TextView log;
    private Button multy_photo;

    private Button djpz;
    private Button djcj;
    public List<LocalMedia> selectList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_take_photo);
        initView();

        String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(permissions, 100);
        }


    }

    File cameraFile;
    File parent = new File(Environment.getExternalStorageDirectory() + File.separator + "myvideos");

    private void takePhotoForCamera() {
        //1  先创建 一个文件  路径 让系统  拍照后  进行存储 (文件夹 + 文件名)


        if (!parent.exists()) {
            parent.mkdirs();
        }

//        cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image2" + System.currentTimeMillis() + ".jpg");
        cameraFile = new File(parent, "head_" + System.currentTimeMillis() + ".jpg");


        if (cameraFile.exists()) {
            cameraFile.delete();
        } else {
            try {
                cameraFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.i("takePhotoForCamera", "takePhotoForCamera: path = > " + cameraFile.getAbsolutePath());


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            //            com.hldj.hmyg.fileprovider
            Uri imageUri = FileProvider.getUriForFile(this, "luocaca.studentdemo.fileprovider", cameraFile);

            Log.i("uri ", "uri: " + imageUri.toString());

            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
        }
        startActivityForResult(intent, TAKE_PHOTO);

        /**
         *   cameraFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "output_image2" + System.currentTimeMillis() + ".jpg");
         clipFile = uploadHeadUtil.getCacheFile(new File(getDiskCacheDir(this)), "clip_image2" + System.currentTimeMillis() + ".jpg");
         //        if (cameraFile.exists()) {
         //            cameraFile.delete();
         //            cameraFile.mkdir();
         //        }

         D.e("cameraFile size" + cameraFile.length());
         //        cacheFile = .getCacheFile(new File(getDiskCacheDir(this)), "handimg.jpg");
         Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
         if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
         imageUri = Uri.fromFile(cameraFile);
         } else {
         intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
         //            com.hldj.hmyg.fileprovider
         imageUri = FileProvider.getUriForFile(mActivity, "com.hldj.hmyg.fileprovider", cameraFile);
         }
         // 启动相机程序
         intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
         startActivityForResult(intent, TAKE_PHOTO);
         */


    }


    public static void start(Activity mActivity) {


        mActivity.startActivity(new Intent(mActivity, TakePhotoActivity.class));

    }

    private void initView() {

        log = (TextView) findViewById(R.id.log);
        log.setOnClickListener(this);


        take = (ImageView) findViewById(R.id.take);
        clip = (ImageView) findViewById(R.id.clip);


        djpz = (Button) findViewById(R.id.djpz);
        djpz.setOnClickListener(this);
        djcj = (Button) findViewById(R.id.djcj);
        djcj.setOnClickListener(this);

        multy_photo = (Button) findViewById(R.id.multy_photo);

        multy_photo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.djpz) {
            //拍照
            takePhotoForCamera();

        } else if (v.getId() == R.id.djcj) {
            //裁剪
            clipPhoto();
        } else if (v.getId() == R.id.log) {
            //裁剪
            上传图片(selectList);
        } else if (v.getId() == R.id.multy_photo) {


            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "myvideos");
            if (!file.exists()) {
                file.mkdirs();
            }


            // 进入相册 以下是例子：用不到的api可以不写
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())//全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
                    .maxSelectNum(20)// 最大图片选择数量 int
                    .minSelectNum(1)// 最小选择数量 int
                    .imageSpanCount(4)// 每行显示个数 int
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
                    .previewImage(true)// 是否可预览图片 true or false
                    .previewVideo(true)// 是否可预览视频 true or false
                    .enablePreviewAudio(true) // 是否可播放音频 true or false
                    .isCamera(true)// 是否显示拍照按钮 true or false
                    .imageFormat(PictureMimeType.JPEG)// 拍照保存图片格式后缀,默认jpeg
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
                    .enableCrop(false)// 是否裁剪 true or false
                    .compress(true)// 是否压缩 true or false
                    .glideOverride(100, 100)// int glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(1, 1)// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(true)// 是否显示uCrop工具栏，默认不显示 true or false
                    .isGif(true)// 是否显示gif图片 true or false
                    .compressSavePath(file.getAbsolutePath())//压缩图片保存地址
//                    .compressSavePath(getExternalCacheDir() + File.separator + "compress")//压缩图片保存地址
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽 true or false
                    .circleDimmedLayer(false)// 是否圆形裁剪 true or false
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
                    .openClickSound(true)// 是否开启点击声音 true or false
                    .selectionMedia(selectList)// 是否传入已选图片 List<LocalMedia> list
                    .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
                    .cropCompressQuality(80)// 裁剪压缩质量 默认90 int
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    .cropWH(1, 1)// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
                    .rotateEnabled(true) // 裁剪是否可旋转图片 true or false
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片 true or false
                    .videoQuality(1)// 视频录制质量 0 or 1 int
                    .videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
                    .videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
                    .recordVideoSecond(60)//视频秒数录制 默认60s int
                    .isDragFrame(false)// 是否可拖动裁剪框(固定)
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
            Toast.makeText(this, "多图选择", Toast.LENGTH_SHORT).show();
        }
    }

    public void  上传图片(List<LocalMedia> selectList) {

    }

    public String cropImagePath;
    public File clipFile;

    private void clipPhoto() {

        clipFile = new File(parent, "/clip" + System.currentTimeMillis() + ".jpg");
        String imagePath = cameraFile.getAbsolutePath();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(new File(imagePath)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(clipFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_PHOTO);
    }


    /**
     * 转换 content:// uri
     *
     * @param imageFile
     * @return
     */
    public Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(cameraFile.getAbsolutePath());

            take.setImageBitmap(bitmap);


        } else if (requestCode == CROP_PHOTO && resultCode == RESULT_OK) {

            Bitmap bitmap = BitmapFactory.decodeFile(clipFile.getAbsolutePath());

            clip.setImageBitmap(bitmap);


        } else if (requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK) {

            // 图片、视频、音频选择结果回调
            selectList = PictureSelector.obtainMultipleResult(data);
            // 例如 LocalMedia 里面返回三种path
            // 1.media.getPath(); 为原图path
            // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
            // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
            // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的

            if (selectList != null) {
                for (int i = 0; i < selectList.size(); i++) {
                    String url = selectList.get(i).getPath();
                    String compressPath = selectList.get(i).getCompressPath();
//                    String videoPath = selectList.get(i).getPath();
                    Log.e("--url--", url);
                    Log.e("---compressPath-", url + "");
                    log.append(url);
                }
            }


        }


    }


}
