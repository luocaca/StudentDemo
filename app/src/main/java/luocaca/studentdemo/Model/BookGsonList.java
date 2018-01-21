package luocaca.studentdemo.Model;

import android.text.TextUtils;
import android.util.Log;

import org.itheima.recycler.bean.BasePageBean;

import java.util.List;

/**
 * Created by Administrator on 2018/1/19 0019.
 */

public class BookGsonList extends BasePageBean<Book> {
    public String code;
    public String msg;
    public List<Book> data;

    private static final String TAG = "BookGsonList";




    @Override
    public List<Book> getItemDatas() {
        Log.i(TAG, "getItemDatas: ");
        if (null != data) {
//            data.forEach(book -> {
//                if (book.url.endsWith("apk")) {
//                    data.remove(book);
//                }
//            });

            Log.i(TAG, "getItemDatas: 过滤开始");

            for (int i = 0; i < data.size(); i++) {
                if (TextUtils.isEmpty(data.get(i).url)
                        || data.get(i).url.endsWith(".apk")
                        || data.get(i).url.endsWith(".rar")
                        || data.get(i).url.contains("192.168.0.13")
                        ) {
                    data.remove(i);
                    Log.i(TAG, "id=" + data.get(i).bookId + "  删除item " + i + " url-> " + data.get(i).url);
                }
            }


//            for (Book datum : data) {
//                if (TextUtils.isEmpty(datum.url) || datum.url.endsWith(".apk") || datum.url.endsWith(".rar")) {
//                    data.remove(datum);
//                    Log.i(TAG, "删除item " + datum.toString());
//                }
//            }

            Log.i(TAG, "getItemDatas: 过滤结束size = " + data.size());

        }

        return data;
    }
}
