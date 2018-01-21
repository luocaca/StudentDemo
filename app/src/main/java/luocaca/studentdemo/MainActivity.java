package luocaca.studentdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.GpsStatus;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import luocaca.studentdemo.Model.Book;
import luocaca.studentdemo.Model.RequestInterceptor;
import luocaca.studentdemo.Reponsity.API;
import luocaca.studentdemo.Utils.AMapUtils;
import luocaca.studentdemo.Utils.LocationUtils_new;
import luocaca.studentdemo.http.FileUtils;
import luocaca.studentdemo.http.UpLoadUtil;
import luocaca.studentdemo.loaction.EasyPermissionsEx;
import luocaca.studentdemo.loaction.LocationHelper;
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


    private Button galary;

    private Book mBook;
    private String path;

    String host = "";

    private String[] mNeedPermissionsList = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.READ_EXTERNAL_STORAGE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
            , Manifest.permission.ACCESS_FINE_LOCATION
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


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
        galary = findViewById(R.id.galary);

        galary.setOnClickListener(this);
        commit.setOnClickListener(this);
        button.setOnClickListener(this);
        button1.setOnClickListener(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


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
                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.button1:
                host = UpLoadUtil.hostRemote;
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType("*/*");
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent1, "Select a File to Upload"), 111);
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

}
