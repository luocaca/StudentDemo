package luocaca.studentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.retrofitutils.ItheimaHttp;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import luocaca.studentdemo.Model.NewsBean;

public class TestRecycleViewActivity extends AppCompatActivity {


    PullToLoadMoreRecyclerView pullToLoadMoreRecyclerView;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.recycler_view)
    ItheimaRecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_recycle_test);
        ButterKnife.bind(this);
        ItheimaHttp.init(getApplicationContext(), "http://www.oschina.net/");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pullToLoadMoreRecyclerView = new PullToLoadMoreRecyclerView<NewsBean>(mSwipeRefreshLayout, mRecyclerView, MyRecyclerViewHolder.class) {
            @Override
            public int getItemResId() {
                //recylerview item资源id
                return R.layout.support_simple_spinner_dropdown_item;
            }




            @Override
            public String getApi() {
                //接口
                return "action/apiv2/news?pageToken=";
                //http://www.oschina.net/action/apiv2/news?pageToken=
            }

//            是否加载更多的数据，根据业务逻辑自行判断，true表示有更多的数据，false表示没有更多的数据，如果不需要监听可以不重写该方法
            @Override
            public boolean isMoreData(BaseLoadMoreRecyclerAdapter.LoadMoreViewHolder holder) {
                System.out.println("isMoreData" + holder);

                return true;
            }
        };


        pullToLoadMoreRecyclerView.requestData();


    }


    public static void start(AppCompatActivity mActivity) {
        mActivity.startActivity(new Intent(mActivity, TestRecycleViewActivity.class));
    }

    private static final String TAG = "TestRecycleViewActivity";


    public static class MyRecyclerViewHolder extends BaseRecyclerViewHolder<NewsBean.Resultbean.Itemsbean> {
        //换成你布局文件中的id
        @BindView(android.R.id.text1)
        TextView tvTitle;

        public MyRecyclerViewHolder(ViewGroup parentView, int itemResId) {
            super(parentView, itemResId);
            Log.i(TAG, "MyRecyclerViewHolder: ");
        }


        /**
         * 绑定数据的方法，在mData获取数据（mData声明在基类中）
         */
        @Override
        public void onBindRealData() {
            tvTitle.setText(mData.title);
            Log.i(TAG, "onBindRealData: "+mData.title);
            Log.i(TAG, "onBindRealData: "+mData.toString());
        }

//        @Override
//        public int getItemViewType(int position) {
//            //根据position返回类型
//            return position % 2 == 0 ? ITEM_TYPE_2 : ITEM_TYPE_1;
//        }




    }

}
