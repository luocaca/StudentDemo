package luocaca.studentdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import luocaca.studentdemo.Model.Book;
import luocaca.studentdemo.Model.RequestInterceptor;
import luocaca.studentdemo.Reponsity.API;
import luocaca.studentdemo.http.FileUtils;
import luocaca.studentdemo.http.UpLoadUtil;
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

    private Book mBook;
    private String path;

    String host = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


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

            mBook.book_id = "10086";

            StringBuilder tempParams = new StringBuilder();

//            tempParams.append("&");
            tempParams.append("book_id=" + mBook.book_id);

            tempParams.append("&");
            tempParams.append("name=" + mBook.name);

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
        mBook.book_id = "";
        mBook.name = getName();
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


}
