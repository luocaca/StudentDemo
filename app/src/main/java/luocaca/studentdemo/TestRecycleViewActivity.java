package luocaca.studentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.retrofitutils.HttpHelper;
import com.itheima.retrofitutils.ItheimaHttp;

import org.itheima.recycler.adapter.BaseLoadMoreRecyclerAdapter;
import org.itheima.recycler.viewholder.BaseRecyclerViewHolder;
import org.itheima.recycler.widget.ItheimaRecyclerView;
import org.itheima.recycler.widget.PullToLoadMoreRecyclerView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import butterknife.BindView;
import butterknife.ButterKnife;
import luocaca.studentdemo.Model.NewsBean;
import luocaca.studentdemo.Model.TestReflectBean;
import luocaca.studentdemo.fragment.WebViewFragment;

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

        //第一种方式获取Class对象
//        HttpHelper helper = HttpHelper.getInstance();
//        Class aClass = helper.getClass();
//        Log.i(TAG, "class name = " + aClass.getName());

        //第2种方式获取Class对象
        Class bClass = HttpHelper.class;
        Log.d(TAG, "class name = " + bClass.getName());


        //第三种方式获取Class对象
//        try {
//            Class cClass = Class.forName("com.itheima.retrofitutils.HttpHelper");
//
//            Log.d(TAG, "cClass =  " + cClass);
//            Log.d(TAG, "hashcode =  " + cClass.hashCode());
//            //同样的类 只会被加载一次
//            Class class2 = Class.forName("com.itheima.retrofitutils.HttpHelper");
//            Log.d(TAG, "hashcode =  " + class2.hashCode());
//
//            Class<String> strClazz = String.class;
//
//            Class<?> strClazz2 = "com.itheima.retrofitutils.HttpHelper".getClass();
//            Log.d(TAG, "strClazz = strClazz2? " + (strClazz == strClazz2));
//
//            Class<?> intClazz = int.class;
//
//            int[] arr01 = new int[10];
//            int[] arr02 = new int[30];
//
//            int[][] arr03 = new int[30][10];
//
//
//            Log.i(TAG, "arr01 == arr02 ? " + (arr01.getClass() == arr02.getClass()));
//
//
//            Log.i(TAG, "arr01 hash = " + arr01.getClass().hashCode());
//            Log.i(TAG, "arr02 hash = " + arr02.getClass().hashCode());
//
//
//            Class cClass5 = Class.forName("com.itheima.retrofitutils.HttpHelper");
//
//            Log.i(TAG, "cClass5 name = " + cClass.getName());
//            Log.i(TAG, "cClass5 simpleName = " + cClass.getSimpleName());
//
//
//            //Declared -- 宣布 公开
//            Field[] fields = cClass5.getDeclaredFields();
//            for (Field field : fields) {
//                Log.i(TAG, "Field:属性  " + field);
//            }
//
//            try {
//                Field field = cClass5.getDeclaredField("sBaseUrl");
//
//                Log.i(TAG, "Field:属性  " + field);
//            } catch (NoSuchFieldException e) {
//                e.printStackTrace();
//            }
//
//            Method[] methods = cClass5.getDeclaredMethods();
//            for (Method method : methods) {
//                Log.i(TAG, "method方法 =   " + method);
//            }
//
//            // public static void setBaseUrl(java.lang.String baseUrl)
//
//            try {
//                Method method = cClass5.getDeclaredMethod("setBaseUrl", "hello-world".getClass());
//                Log.i(TAG, "method方法 =   " + method);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//            try {
//                Method method = cClass5.getDeclaredMethod("getBaseUrl");
//                Log.i(TAG, "method方法 =   " + method);
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//
//            //获取构造信息
//            Constructor[] constructors = cClass5.getDeclaredConstructors();
//            Log.i(TAG, "构造器 无参=   " + constructors);
//
//            //单独获取 ， 无参数
//            Constructor constructor = cClass5.getDeclaredConstructor();
//            System.out.print("构造器 --- 》" + constructor);
//
//            //单独获取  ， 有参数
////            Constructor constructor1 = cClass5.getDeclaredConstructor(int.class,int.class,string.class);
//
//
//            Log.i(TAG, "class name = " + cClass.getName());
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

        // private HttpHelper() { /* compiled code */ }
        //通过反射 动态操作 构造器 ，方法 ，属性
        ItheimaHttp.init(getApplicationContext(), "http://www.oschina.net/");
//        ItheimaHttp.init(getApplicationContext(), "http://www.oschina.net/");

        try {
            Class clazz = Class.forName("com.itheima.retrofitutils.HttpHelper");


            Class<TestReflectBean> modelClass = TestReflectBean.class;

            Constructor d = modelClass.getDeclaredConstructor();
            d.setAccessible(true);
            TestReflectBean bean = (TestReflectBean) d.newInstance();

            Log.i(TAG, "反射前 getNumber = " + bean.getNumber());

            Method method = bean.getClass().getDeclaredMethod("setNumber", String.class);
//            Method method = bean.getClass().getMethod("setNumber",String.class);
            method.setAccessible(true);
            method.invoke(bean, "i im a num");
            Log.i(TAG, "反射后 getNumber = " + bean.getNumber());


            Log.i(TAG, "onCreate: " + bean.getBookId());


//            Class c = HttpHelper.class;
//            Constructor ccc =  c.getDeclaredConstructor();
//            ccc.setAccessible(true);

            //clazz
            /**
             * <code>Class.newInstance()只能反射无参的构造器；
             Constructor.newInstance()可以反任何构造器；

             Class.newInstance()需要构造器可见(visible)；
             Constructor.newInstance()可以反私有构造器；

             Class.newInstance()对于捕获或者未捕获的异常均由构造器抛出;
             Constructor.newInstance()通常会把抛出的异常封装成InvocationTargetException抛出；
             </code>
             */


            //            HttpHelper helper = (HttpHelper) clazz.newInstance();
//          Field field = clazz.getField("sBaseUrl");
            Field field = clazz.getDeclaredField("sBaseUrl");

            field.setAccessible(true);
            field.set(null, "http://www.oschina.net/");

            Log.i(TAG, "onCreate: " + Modifier.isStatic(field.getModifiers()));

//            field.set(null,"sBaseUrl", "http://www.oschina.net/");

//            helper.sBaseUrl


            //private static WeakReference<HttpHelper> sInstance;
            Field sInstance = clazz.getDeclaredField("sInstance");
            sInstance.setAccessible(true);
            sInstance.set(null, null);


            //获取无参 构造方法  把 private 变成 public
            Constructor constructor = clazz.getDeclaredConstructor();
            Log.i(TAG, "setAccessible: " + constructor.isAccessible());
            constructor.setAccessible(true);
            HttpHelper helper = (HttpHelper) constructor.newInstance();
            Log.i(TAG, "setAccessible: " + constructor.isAccessible());

//            Constructor constructor1 = clazz.getDeclaredConstructor();
//            Log.i(TAG, "setAccessible: "+constructor1.isAccessible());


            Log.i(TAG, "getBaseUrl= " + helper.getBaseUrl());


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


//        HttpHelper.setBaseUrl("http://www.oschina.net/");
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
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = WebViewFragment.newInstance(mData.href);
                    ((FragmentActivity) v.getContext()).getSupportFragmentManager()
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .addToBackStack(fragment.getClass().getName())
                            .replace(android.R.id.content, fragment)
                            .commit();
                }

            });
            Log.i(TAG, "onBindRealData: " + mData.title);
            Log.i(TAG, "onBindRealData: " + mData.toString());
        }

//        @Override
//        public int getItemViewType(int position) {
//            //根据position返回类型
//            return position % 2 == 0 ? ITEM_TYPE_2 : ITEM_TYPE_1;
//        }


    }

}
