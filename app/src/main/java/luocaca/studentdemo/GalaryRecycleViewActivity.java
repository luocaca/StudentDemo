package luocaca.studentdemo;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.itheima.retrofitutils.ItheimaHttp;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import luocaca.studentdemo.Model.Book;
import luocaca.studentdemo.Model.BookGsonList;
import luocaca.studentdemo.viewbigimage.ViewBigImageActivity;
import okhttp3.Headers;

public class GalaryRecycleViewActivity extends AppCompatActivity {


    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    ItheimaRecyclerView mRecyclerView;
    private StaggeredGridLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listBooks.clear();
        setContentView(R.layout.activity_recycle_test);
        ButterKnife.bind(this);
//        ItheimaHttp.init(getApplicationContext(), "http://www.oschina.net/");

        ItheimaHttp.init(getApplicationContext(), "http://www.luocaca.cn/");


        Class clazz = null;
        try {
            clazz = Class.forName("com.itheima.retrofitutils.HttpHelper");
            //            HttpHelper helper = (HttpHelper) clazz.newInstance();
//          Field field = clazz.getField("sBaseUrl");
            Field field = clazz.getDeclaredField("sBaseUrl");
            field.setAccessible(true);
            field.set(null, "http://www.luocaca.cn/");


            //private static WeakReference<HttpHelper> sInstance;
            Field sInstance = clazz.getDeclaredField("sInstance");
            sInstance.setAccessible(true);
            sInstance.set(null, null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }


        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//不设置的话，图片闪烁错位，有可能有整列错位的情况。

//        layoutManager.invalidateSpanAssignments();
        mRecyclerView.setLayoutManager(layoutManager);//设置瀑布流管理器


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//这行主要解决了当加载更多数据时，底部需要重绘，否则布局可能衔接不上。
            }
        });

//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<BookGsonList>(mSwipeRefreshLayout, mRecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.item_galary;
            }


            @Override
            public String getApi() {
                //接口
                return "hello-ssm/book/allbook";
//                return "action/apiv2/news?pageToken=";
                //http://www.oschina.net/action/apiv2/news?pageToken=
            }

            //            是否加载更多的数据，根据业务逻辑自行判断，true表示有更多的数据，false表示没有更多的数据，如果不需要监听可以不重写该方法
            @Override
            public boolean isMoreData(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder) {
                System.out.println("isMoreData" + holder);

                return true;
            }
        };


        pullToLoadMoreRecyclerView.setLoadingDataListener(new PullToLoadMoreRecyclerView
                .LoadingDataListener<BookGsonList>() {

            @Override
            public void onSuccess(BookGsonList bookGsonListBasePageBean, Headers headers) {
//                listBooks.addAll(bookGsonListBasePageBean.getItemDatas());
                bookGsonList = bookGsonListBasePageBean;
//                super.onSuccess(bookGsonListBasePageBean, headers);
                Log.i(TAG, "onSuccess: ");
            }
        });
        pullToLoadMoreRecyclerView.requestData();


    }

    static BookGsonList bookGsonList;
    public static List<Book> listBooks = Arrays.asList();


    public static ArrayList<String> getBooks(BookGsonList list) {

        ArrayList<String> lists = new ArrayList<>();
        for (Book listBook : list.data) {
            lists.add(listBook.url);
        }


        return lists;
    }


    public static void start(AppCompatActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, GalaryRecycleViewActivity.class));
    }

    private static final String TAG = "TestRecycleViewActivity";


    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<Book> {
        //换成你布局文件中的id
//        @BindView(android.R.id.text1)
//        TextView tvTitle;

        @BindView(R.id.imageView)
        ImageView imageView;

        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
            Log.i(TAG, "MyRecyclerViewHolder: ");
        }


        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {

            if (
                    mData.url.endsWith(".apk")
                            || mData.url.endsWith(".rar")
                            || mData.url.contains("192.168.0.13")
                    ) {
                imageView.setVisibility(View.GONE);
                return;
            } else {
                imageView.setVisibility(View.VISIBLE);
            }


            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);

//            tvTitle.setText(mData.url);
            Glide.with(mContext).applyDefaultRequestOptions(options).load(mData.url).into(imageView);


            imageView.setOnClickListener(v -> {
                Toast.makeText(mContext, "id=" + mData.url, Toast.LENGTH_SHORT).show();
                // 从API11开始android推荐使用android.content.ClipboardManager
                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setPrimaryClip(ClipData.newPlainText(null, mData.url));
                Toast.makeText(mContext, "复制地址成功，可以到流浪器去下载。", Toast.LENGTH_LONG).show();


                Bundle bundle = new Bundle();
                bundle.putInt("selet", mPosition);// 2,大图显示当前页数，1,头像，不显示页数
                bundle.putInt("code", mPosition);//第几张
                bundle.putStringArrayList("imageuri", getBooks(bookGsonList));
                Intent intent = new Intent(mContext, ViewBigImageActivity.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);


            });

            Log.i(TAG, "onBindRealData: " + mData.url);
            Log.i(TAG, "onBindRealData: " + mData.toString());

        }

//        @Override
//        public int getItemViewType(int position) {
//            //根据position返回类型
//            return position % 2 == 0 ? ITEM_TYPE_2 : ITEM_TYPE_1;
//        }


    }

}
