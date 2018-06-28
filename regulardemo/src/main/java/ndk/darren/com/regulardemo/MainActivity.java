package ndk.darren.com.regulardemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {


    EditText et_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        et_json = (EditText) findViewById(R.id.et_json);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


                testPatter();


            }


        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * { address:http://t.cn/R1IHhGG ,img:http://xxnoss.gulutu.com/public/attachment/201805/29/15/origin/1527550657835474.jpg?x-oss-process=image/resize,m_mfit,h_260,w_260,
     * title:kss 领风骚},
     */

    /**
     * .compile("((?:(http|https|Http|Https|rtsp|Rtsp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)"
     * + "\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_"
     * + "\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?"
     * + "((?:(?:["
     * + GOOD_IRI_CHAR
     */
    private void testPatter() {

        String json = et_json.getText().toString();

//        Log.i("patter", json);

//        String pattern = "http://t.cn/\\\\[a-zA-Z0-9][7]";
//        String pattern = "http+://t.cn/\\w{7}"; // 解析 网址
        //(s|S)(r|R)(c|C) *= *('|")?(\w|\\|\/|\.)+('|"| *|>)?
//        String pattern = "http+://*.jpg";// 解析图片地址
//        String pattern = "(s|S)(r|R)(c|C) *= *('|\")?(\\w|\\\\|\\/|\\.)+('|\"| *|>)?";// 解析图片地址
        //String regex = ".*\\.png";
        //String regex  = "^ *bt.*st *$";
//        String pattern = "^*http.*jpg$";// 解析图片地址
//        String pattern = "^http+([\\s\\S]*?)+jpg$";// 解析图片地址
        /**
         * 5、数量表示：之前所有的正则都只是表示一位，如果要表示多位，则就需要数量表示。

         （1）正则表达式？：此正则出现0次或1次；

         （2）正则表达式*：此正则出现0次、1次或多次；

         （3）正则表达式+：次正则出现1次或多次；

         （4）正则表达式{n}：此正则出现正好n次；

         （5）正则表达式{n,}：此正则出现n次以上；

         （6）正则表达式{n,m}：此正则出现n – m次。
         */
//        String pattern = "http://(?!(\\.jpg|\\.png).+?(\\.jpg|\\.png))";// 解析图片地址
//        String pattern = "http://(\\*.+(\\.jpg))";// 解析图片地址
//        String pattern ="http://xxnoss.gulutu.com(?!(\\.jpg|\\.png)).+?(\\.jpg|\\.png)";// 解析图片地址
        String pattern = "http://t.cn/\\w{7}";
        /**
         * (^[ ]?bt)  以'  bt '开头
         ([ ]?st$)  以'st' 和' st'结尾
         */

        // matches 匹配
        Pattern compile = Pattern.compile(pattern);

        Matcher matcher = compile.matcher(json);
        while (matcher.find()) {
            //I/matcher->: http://t.cn/R1fscZ7,  B7I9O]\'

            // img:
            // http://xxnoss.gulutu.com/public/attachment/201805/24/23/origin/1527147170804166.jpg
            Log.i("matcher->", matcher.group());
        }


//        Toast.makeText(this, "isMatches=" + Arrays.toString(htt), Toast.LENGTH_SHORT).show();


    }


}
