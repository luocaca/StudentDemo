package luocaca.studentdemo;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.GpsStatus;
import android.location.Location;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.example.maventest.EsayVideoEditActivity;
import com.example.maventest.OnProcessListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import luocaca.studentdemo.Model.Book;
import luocaca.studentdemo.Model.RequestInterceptor;
import luocaca.studentdemo.Reponsity.API;
import luocaca.studentdemo.Utils.AMapUtils;
import luocaca.studentdemo.Utils.LocationUtils_new;
import luocaca.studentdemo.http.FileUtils;
import luocaca.studentdemo.http.UpLoadUtil;
import luocaca.studentdemo.http.UploadRunnable;
import luocaca.studentdemo.i.OnImageUploadListener;
import luocaca.studentdemo.loaction.EasyPermissionsEx;
import luocaca.studentdemo.loaction.LocationHelper;
import luocaca.studentdemo.ui.MyTakePhotoActivity;
import me.shaohui.advancedluban.Luban;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private EditText et_detail;
    private EditText et_number;
    private EditText et_name;
    private Button commit;
    private Button button;
    private Button button1;

    private Button button2;

    private Button galary;

    private Book mBook;
    private String path;

    private ExecutorService executorService;

    private MainActivity mActivity;


    String host = "";

    private String[] mNeedPermissionsList = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_FINE_LOCATION
    };
    private Button testvideo;
    private Button video_select;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mActivity = this;
        executorService = Executors.newFixedThreadPool(10);
//        requestLoaction(this);
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION
//                , Manifest.permission.READ_EXTERNAL_STORAGE
//                , Manifest.permission.WRITE_EXTERNAL_STORAGE
//                , Manifest.permission.ACCESS_FINE_LOCATION
//        }, 100);


        // 使用了 EasyPermissionsEx 类来管理动态权限配置
        if (EasyPermissionsEx.hasPermissions(this, mNeedPermissionsList)) {
//            initLocation(this);
            AMapUtils.getInstance(getApplicationContext())
                    .setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {
                            et_detail.append(aMapLocation.getLocationType() + "\n");
                            et_detail.append(aMapLocation.getLatitude() + "\n");
                            et_detail.append(aMapLocation.getLongitude() + "\n");
                            et_detail.append(aMapLocation.getAccuracy() + "\n");
                            et_detail.append(aMapLocation.getAddress() + "\n");
                            et_detail.append(aMapLocation.getCountry() + "\n");
                            et_detail.append(aMapLocation.getProvince() + "\n");
                            et_detail.append(aMapLocation.getCity() + "\n");
                            et_detail.append(aMapLocation.getDistrict() + "\n");
                            et_detail.append(aMapLocation.getStreet() + "\n");
                            et_detail.append(aMapLocation.getStreetNum() + "\n");
                            et_detail.append(aMapLocation.getCityCode() + "\n");
                            et_detail.append(aMapLocation.getAdCode() + "\n");
                            et_detail.append(aMapLocation.getAoiName() + "\n");
                            et_detail.append(aMapLocation.getBuildingId() + "\n");
                            et_detail.append(aMapLocation.getFloor() + "\n");
                            et_detail.append(aMapLocation.getGpsAccuracyStatus() + "\n");
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            Date date = new Date(aMapLocation.getTime());
                            df.format(date);
                            et_detail.append(df.toLocalizedPattern());
                            // Log.i("onLocation", aMapLocation.getAddress() + " thread = " + Thread.currentThread().getName());
//                            Log.i("onLocation", aMapLocation.getCity());
                        }
                    })
                    .requestLocationUpdates();

        } else {
            EasyPermissionsEx.requestPermissions(this, "需要定位权限来获取当地天气信息", 1, mNeedPermissionsList);
        }
//
//        LocationUtils.getInstance(getApplicationContext())
//                .requestLocationUpdates(new LocationUtils.CLocationListener() {
//
//                    @Override
//                    protected void finalize() throws Throwable {
//                        super.finalize();
//
//                        Log.w(TAG, "finalize: ");
//                    }
//
//                    @Override
//                    public void onLocationChanged(Location location) {
//                        Log.d(TAG, "onLocationChanged: \n" + location.toString());
//                        Toast.makeText(MainActivity.this, "" + location.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                });


    }


    /**
     * 请求  添加图书
     */
    private void requestBookAdd() {
        //网络异步请求，回调json

        mBook = BookConsermer();

        try {
            String baseUrl = API.BASE_URL;
            Log.i(TAG, "baseUrl...." + baseUrl);

            mBook.bookId = "10086";

            StringBuilder tempParams = new StringBuilder();

//            tempParams.append("&");
            tempParams.append("book_id=" + mBook.bookId);

            tempParams.append("&");
            tempParams.append("name=" + mBook.detail);

            tempParams.append("&");
            tempParams.append("detail=" + mBook.detail);

            tempParams.append("&");
            tempParams.append("number=" + mBook.number);

            Log.i(TAG, "参数：" + tempParams);


//            String str = "name=二傻&book_id=222222&number=222222&detail=二傻么么哒";

//            byte[] postData = tempParams.toString().getBytes();
            byte[] postData = tempParams.toString().getBytes();


//            baseUrl = http://192.168.0.4:8089/hello/book/list/add?

            String BASE_URL = "http://localhost:8089/hello/book/add?addname=大傻大傻大傻&book_id=111&number=222&detail=111";

//    ?name=大傻大傻大傻&book_id=111&number=222&detail=111

            //新建URL对象
            URL url = new URL(BASE_URL);
            // 打开一个httpURLconnection 连接
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //设置连接超时时间
            urlConnection.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时时间
            urlConnection.setReadTimeout(5 * 1000);

            // post 请求必须设置 允许输出  默认false
            urlConnection.setDoOutput(true);

            //设置请求允许输入  默认true
            urlConnection.setDoInput(true);

            //post 请求不能使用缓存
            urlConnection.setUseCaches(false);

            urlConnection.setRequestMethod("GET");

            //设置本次连接是否自动处理重定向
            urlConnection.setInstanceFollowRedirects(true);

            //配置请求 content -type
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");


            // 开始连接   ----进行3次握手，4次挥手
            urlConnection.connect();

            DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
//            dos.write(postData);
            dos.flush();
            dos.close();

            if (urlConnection.getResponseCode() == 200) {
                //
                Log.i(TAG, "request code = " + 200);

                String result = stremToString(urlConnection.getInputStream());
                Toast.makeText(this, "成功了" + result, Toast.LENGTH_SHORT).show();

            } else {
                Log.i(TAG, "requestBookAdd: 网络请求失败")
                ;

                String result = stremToString(urlConnection.getInputStream());

                Toast.makeText(this, "失败了" + urlConnection.getResponseCode() + " " + result, Toast.LENGTH_SHORT).show();

                Log.i(TAG, "requestBookAdd: 网络请求失败" + urlConnection.getResponseCode());


            }
            urlConnection.disconnect();


        } catch (Exception e) {
            Toast.makeText(this, "报错了：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    private String stremToString(InputStream inputStream) {
        try {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }


            baos.close();
            ;
            inputStream.close();

            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);

        } catch (IOException e) {


            e.printStackTrace();
            return null;

        }
    }


    private void initView() {
        et_detail = (EditText) findViewById(R.id.et_detail);
        et_number = (EditText) findViewById(R.id.et_number);
        et_name = (EditText) findViewById(R.id.et_name);
        commit = (Button) findViewById(R.id.commit);
        button = (Button) findViewById(R.id.button);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        testvideo = (Button) findViewById(R.id.testvideo);
        video_select = (Button) findViewById(R.id.video_select);
        galary = findViewById(R.id.galary);

        galary.setOnClickListener(this);
        commit.setOnClickListener(this);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        testvideo.setOnClickListener(this);
        video_select.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 101 && resultCode == RESULT_OK) {
            Toast.makeText(mActivity, "选择成功", Toast.LENGTH_SHORT).show();

            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(TAG, "File Uri: " + uri.toString());
            // Get the path
            path = null;
//            try {
//                path = FileUtils.getVideoPath(this, uri);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }

            ContentResolver cr = this.getContentResolver();
            /** 数据库查询操作。
             * 第一个参数 uri：为要查询的数据库+表的名称。
             * 第二个参数 projection ： 要查询的列。
             * 第三个参数 selection ： 查询的条件，相当于SQL where。
             * 第三个参数 selectionArgs ： 查询条件的参数，相当于 ？。
             * 第四个参数 sortOrder ： 结果排序。
             */
            Cursor cursor = cr.query(uri, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    // 视频ID:MediaStore.Audio.Media._ID
                    int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    // 视频名称：MediaStore.Audio.Media.TITLE
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    // 视频路径：MediaStore.Audio.Media.DATA
                    String videoPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    // 视频时长：MediaStore.Audio.Media.DURATION
                    int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                    // 视频大小：MediaStore.Audio.Media.SIZE
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                    // 视频缩略图路径：MediaStore.Images.Media.DATA
                    String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    path = imagePath;
                    // 缩略图ID:MediaStore.Audio.Media._ID
                    int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
                    // 方法一 Thumbnails 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                    // 第一个参数为 ContentResolver，第二个参数为视频缩略图ID， 第三个参数kind有两种为：MICRO_KIND和MINI_KIND 字面意思理解为微型和迷你两种缩略模式，前者分辨率更低一些。
                    Bitmap bitmap1 = MediaStore.Video.Thumbnails.getThumbnail(cr, imageId, MediaStore.Video.Thumbnails.MICRO_KIND, null);

                    // 方法二 ThumbnailUtils 利用createVideoThumbnail 通过路径得到缩略图，保持为视频的默认比例
                    // 第一个参数为 视频/缩略图的位置，第二个依旧是分辨率相关的kind
                    Bitmap bitmap2 = ThumbnailUtils.createVideoThumbnail(imagePath, MediaStore.Video.Thumbnails.MICRO_KIND);
                    // 如果追求更好的话可以利用 ThumbnailUtils.extractThumbnail 把缩略图转化为的制定大小
//                        ThumbnailUtils.extractThumbnail(bitmap, width,height ,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

                }
                cursor.close();
            }


            Log.d(TAG, "File Path: " + path);


            return;


        }

        if (requestCode == 100 && resultCode == RESULT_OK) {
            Toast.makeText(mActivity, "RESULT_OK", Toast.LENGTH_SHORT).show();
            return;
        }


        if (resultCode == RESULT_OK) {
            // Get the Uri of the selected file
            Uri uri = data.getData();
            Log.d(TAG, "File Uri: " + uri.toString());
            // Get the path
            path = null;
            try {
                path = FileUtils.getPath(this, uri);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "File Path: " + path);

            Log.i(TAG, "onActivityResult: " + getMimeType(path));
            ;
            Log.i(TAG, "onActivityResult: " + getMimeType(path));
            ;
            Log.i(TAG, "onActivityResult: " + getMimeType(path));
            ;
            Log.i(TAG, "onActivityResult: " + getMimeType(path));
            ;

            if ("video/mp4".equals(getMimeType(path))) {
                Log.i(TAG, "上传视频");
                //上传视频
                Observable.just(path)
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                String result = UpLoadUtil.testUploadImage(s, host, MainActivity.this);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        et_detail.setText(result);
                                    }
                                });

                            }
                        });

            } else {
//        File file = new File(inputValue);
//                    Luban.compress(activity,file);
                Log.i(TAG, "上传图片");
                Luban.compress(MainActivity.this, new File(path))
                        .putGear(Luban.THIRD_GEAR)      // set the compress mode, default is : THIRD_GEAR
                        .setCompressFormat(Bitmap.CompressFormat.JPEG)
                        .asObservable()
                        .observeOn(Schedulers.io())
                        .subscribe(new Consumer<File>() {
                            @Override
                            public void accept(File file) throws Exception {
                                String result = UpLoadUtil.testUploadImage(file.getAbsolutePath(), host, MainActivity.this);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        et_detail.setText(result);
                                    }
                                });

                            }
                        })

                ;              // start compression and set the listener

            }


//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    UpLoadUtil.testUploadImage(path, host, MainActivity.this);
//
//                }
//            }).start();

            // Get the file instance
            // File file = new File(path);
            // Initiate the upload
        } else if (resultCode == 1) {
            Toast.makeText(this, "settings", Toast.LENGTH_LONG).show();

        }
    }


    int count = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.video_select:

//                Intent it=new Intent(Intent.ACTION_GET_CONTENT);
//
//                it.setType("audio/*");//选取所有的音乐类型 *有mp3、wav、mid等

//                startActivityForResult(it,100);//以识别编号来启动外部程序

                Intent it = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);

// MediaStore.Video.Media.EXTERNAL_CONTENT_URI

                it.setType("video/mp4");//选取所有的视频类型 *有mp4、3gp、avi等

                startActivityForResult(it, 101);//以识别编号来启动外部程序

//                Intent it=new Intent(Intent.ACTION_GET_CONTENT);
//
//                it.setType("image/*");//选取所有的图片类型 * png和jpeg等
//
//                startActivityForResult(it,102);//以识别编号来启动外部程序
//
//                ---------------------------


                break;

            case R.id.testvideo:

                EsayVideoEditActivity.onProcessListener = new OnProcessListener() {
                    @Override
                    public void onStart() {
                        et_detail.append("开始" );
                        Log.i(TAG, "onStart: ");
                    }

                    @Override
                    public void onClipSuccess(String path) {
                        Log.i(TAG, "onClipSuccess: " + path);
                        et_detail.append("裁剪成功" + path);
                    }

                    @Override
                    public void onCompressSuccess(String path) {
                        Log.i(TAG, "onCompressSuccess: " + path);


                        et_detail.append("压缩成功" + path);

                    }

                    @Override
                    public void onFailed(String msg) {
                        et_detail.append("压缩失败" + msg);
                        Log.i(TAG, "onFailed: " + msg);
                    }

                    @Override
                    public void onFinish() {
                        et_detail.append("压缩结束");
                        Log.i(TAG, "onFinish: ");
                    }
                };


                String video = Environment.getExternalStorageDirectory().getPath() + File.separator
                        + "myvideos" + File.separator + "a1.mp4";

                if (new File(video).exists()) {
                    Log.i(TAG, "存在此视频: ");
                } else {
                    Log.w(TAG, "不 存在此视频: ");
                }

                Intent intent1 = new Intent();
                intent1.putExtra(EsayVideoEditActivity.PATH, path);
                intent1.setClass(this, EsayVideoEditActivity.class);
                EsayVideoEditActivity.isOk2Finish = true;
                startActivityForResult(intent1, 100);

                break;
            case R.id.button2:
                Toast.makeText(this, "上传glide 下面所有图片", Toast.LENGTH_SHORT).show();
//                GlideCache

                // 直接上传文件  所有  jpg文件
                File glideCacheDir = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "GlideCache");

                if (glideCacheDir.exists()) {
                    File[] files = glideCacheDir.listFiles();
//                  files = new File[]{files[50],files[61]};

                    Log.i(TAG, "一共图片 （张）: --->" + files.length);


                    Observable.fromArray(files)
                            .filter(new Predicate<File>() {
                                @Override
                                public boolean test(File file) throws Exception {

                                    count++;

                                    if (file.length() / 1024 > 40) {

                                        return true;
                                    } else {
                                        return false;
                                    }

//                                    if (file.getName().endsWith(".jpg")) {
//
//                                        return true;
//
//                                    } else if (file.getName().endsWith(".0")) {
//
//                                        Log.i(TAG, "test: before ---> " + file.getName());
//
//                                        file.renameTo(new File(glideCacheDir.getAbsoluteFile(), System.currentTimeMillis() + count + ".jpg"));
//
//                                        Log.i(TAG, "test: after ---> " + file.getName());
//                                        /**
//                                         *File from =new File(sdCard,"from.txt") ;
//                                         File to=new File(sdCard,"to.txt") ;
//                                         from.renameTo(to) ;   重命名sd卡文件的
//                                         */
//                                        return true;
//                                    } else {
//                                        return true;
//                                }
                                }
                            })

//                            .flatMap(file ->
//                                    Luban.compress(file, getCacheDir())
//                                            .asObservable())
                            .onExceptionResumeNext(new Observable<File>() {
                                @Override
                                protected void subscribeActual(Observer<? super File> observer) {
                                    Log.w(TAG, "subscribeActual: 失败后继续");
                                }
                            })
                            .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends File>>() {
                                @Override
                                public ObservableSource<? extends File> apply(Throwable throwable) throws Exception {
                                    Log.w(TAG, "apply: onErrorResumeNext  ");
                                    return Observable.<File>empty();
                                }
                            })
                            .filter(file -> file != null)
                            .subscribe(new Consumer<File>() {

                                @Override
                                public void accept(File file) throws Exception {

                                    Log.i(TAG, "File name = :    " + file.getName() + "  size = " + file.length() / 1024);

//                                    Log.i(TAG, "accept: is delete succeed ？ " + file.delete());


                                    executorService.execute(new UploadRunnable(new OnImageUploadListener() {
                                        @Override
                                        public void onSucceed(String json) {
                                            Log.i(TAG, "onSucceed" + json);

                                            et_detail.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    et_detail.append("\n" + json);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onFailed(String msg) {
                                            Log.i(TAG, "onFailed" + msg);
                                        }
                                    }, file, mActivity));


                                }
                            });
//                    for (File file : files) {
//                        Log.i(TAG, "File name = :    " + file.getName());
//                    }
                }


                break;

            case R.id.button1:
                host = UpLoadUtil.hostRemote;
                Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                intent2.setType("*/*");
                intent2.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent2, "Select a File to Upload"), 111);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.button:

                host = UpLoadUtil.hostLocal;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), 111);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }


                break;
            case R.id.galary:
                //跳转 图片浏览器

                GalaryRecycleViewActivity.start(this);


                break;
            case R.id.commit:

//                submit();

                TestRecycleViewActivity.start(this);


//                Gson gson = new GsonBuilder()
//                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
//                        .create();
//
//
//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(API.BASE_URL)
//                        .addConverterFactory(GsonConverterFactory.create(gson))
//                        .client(getOkHttpClient())
//                        .build();
//
//
//                ServiceAdd serviceAdd = retrofit.create(ServiceAdd.class);
//
//
//                Call<String> call = serviceAdd.requestAdd(
//                        BookConsermer().book_id,
//                        BookConsermer().number,
//                        BookConsermer().detail,
//                        BookConsermer().name);
//                call.enqueue(new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        int statusCode = response.code();
//                        Log.i(TAG, "onResponse: " + response.body() + "   code = " + statusCode + " call=" + call);
//                    }
//
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//                        Log.e(TAG, "onResponse: " + t.getMessage());
//                    }
//                });

                break;
        }

    }


    private Book BookConsermer() {
        mBook = new Book();
        mBook.bookId = "";
        mBook.url = getName();
        mBook.detail = getDetail();
        mBook.number = getNumber();
        return mBook;
    }


    public String getDetail() {
        return et_detail.getText().toString().trim();
    }


    public String getNumber() {
        return et_number.getText().toString().trim();
    }

    public String getName() {
        return et_name.getText().toString().trim();
    }

    private void submit() {
        // validate
        if (TextUtils.isEmpty(getDetail())) {
            Toast.makeText(this, "输入本图书的详细描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(getNumber())) {
            Toast.makeText(this, "请输入图书编号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(getName())) {
            Toast.makeText(this, "请输入图书名称", Toast.LENGTH_SHORT).show();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {


                requestBookAdd();


            }
        }).start();


        Toast.makeText(this, BookConsermer().toString(), Toast.LENGTH_SHORT).show();

        // TODO validate success, do something


    }


    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        RequestInterceptor loggingInterceptor = new RequestInterceptor();
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }

    public static String getMimeType(String fileName) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String type = fileNameMap.getContentTypeFor(fileName);
        return type;
    }


    public void requestLoaction(Activity mActivity) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: ");
//                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {// 没有权限。
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {

                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                } else {
                    // 申请授权。
                    ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
                }
//                } else {
                Log.i(TAG, "run: ");
//                    Intent intent = new Intent(mActivity, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }

            }

        }, 1500);
    }


    private void initLocation(Activity mActivity) {
        LocationUtils_new.getInstance(mActivity).initLocation(new LocationHelper() {
            @Override
            public void UpdateLocation(Location location) {
                Log.e("MoLin", "location.getLatitude():" + location.getLatitude());
                et_name.append(location.getLatitude() + "");
                et_name.append(location.getLongitude() + "");
            }

            @Override
            public void UpdateStatus(String provider, int status, Bundle extras) {
            }

            @Override
            public void UpdateGPSStatus(GpsStatus pGpsStatus) {

            }

            @Override
            public void UpdateLastLocation(Location location) {
                Log.e("MoLin", "UpdateLastLocation_location.getLatitude():" + location.getLatitude());
                et_name.append(location.getLatitude() + "");
                et_name.append(location.getLongitude() + "");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            Intent intent = new Intent();
//            intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
//            intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setAction("android.intent.action.VIEW");
//            startActivity(intent);
//        } catch (Exception e) {
//        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("MoLin", "已获取权限!");
//                    initLocation(MainActivity.this);
                    AMapUtils.getInstance(getApplicationContext())
                            .requestLocationUpdates();
                } else {
                    if (EasyPermissionsEx.somePermissionPermanentlyDenied(this, mNeedPermissionsList)) {
                        EasyPermissionsEx.goSettings2Permissions(this, "需要定位权限来获取当地天气信息,但是该权限被禁止,你可以到设置中更改"
                                , "去设置", 1);
                    }
                }
            }
            break;

        }
    }


    @Override
    protected void onDestroy() {
        // 在页面销毁时取消定位监听
        LocationUtils_new.getInstance(this).removeLocationUpdatesListener();
        super.onDestroy();
    }

    public void testPhoto(View view) {

//        TakePhotoActivity.start(this);
        MyTakePhotoActivity.start(this);


    }
}
