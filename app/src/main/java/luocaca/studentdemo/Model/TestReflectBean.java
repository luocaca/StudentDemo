package luocaca.studentdemo.Model;

import android.util.Log;

/**
 * Created by Administrator on 2017/10/15 0015.
 */

public class TestReflectBean {
    //{"bookId":7065,
    // "number":0,
    // "detail":"安卓手机图片上传",
    // "url":"http://www.luocaca.cn/hello-ssm/upload/30ff2852502f313d01888e2542d426b5f918ca0cec834c80a9815dd4ebfbb9cf.0"},

    public String bookId = "10086";
    public String number;
    public String detail;
    public String url;


    private TestReflectBean() {

    }

    private void setNumber(String num) {
        this.number = num;
        Log.i("TestReflectBean", "TestReflectBean: ");
    }

    public String getNumber() {
        return number;
    }

    public String getBookId() {
        return bookId;
    }


}
