package luocaca.studentdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import luocaca.studentdemo.R;

/**
 * Created by Administrator on 2018/1/21 0021.
 */

//public class WebViewFragment extends android.webkit.WebViewFragment {
public class WebViewFragment extends Fragment {

    private static final String TAG = "WebViewFragment";

    private WebView mWebView;
    private boolean mIsWebViewAvailable;


    private View mContentView;


    @BindView(R.id.web)
    WebView web;


    public static WebViewFragment newInstance(String initUrl) {
        WebViewFragment viewFragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TAG, initUrl);
        viewFragment.setArguments(bundle);
        return viewFragment;
    }

    public String getUrl() {
        return getArguments().getString(TAG);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        mContentView = inflater.inflate(R.layout.fragment_webview, null);
//
//        ButterKnife.bind(mContentView);
//        initView(mContentView);


        if (mWebView != null) {
            mWebView.destroy();
        }
        mWebView = new WebView(getContext());
        mIsWebViewAvailable = true;
        initView(mWebView);

        return mWebView;

//        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: ");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");

//        if (getWebView() != null) {
//            getWebView().loadUrl(getUrl());
//        }


    }

    private void initView(WebView webView) {
//        webView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onShowCustomView(View view, CustomViewCallback callback) {
//                super.onShowCustomView(view, callback);
//            }
//
//
//        });
//
//        webview_in = (WebView) findViewById(R.id.webview_in);
//        //web资源
        webView.loadUrl(getUrl());
        //设置WebViewClient客户端
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

//        webView.loadUrl("http://www.luocaca.cn/hello-ssm");
    }


    /**
     * Called when the fragment is visible to the user and actively running. Resumes the WebView.
     */
    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    /**
     * Called when the fragment is no longer resumed. Pauses the WebView.
     */
    @Override
    public void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    /**
     * Called when the WebView has been detached from the fragment.
     * The WebView is no longer available after this time.
     */
    @Override
    public void onDestroyView() {
        mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    /**
     * Called when the fragment is no longer in use. Destroys the internal state of the WebView.
     */
    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    /**
     * Gets the WebView.
     */
    public WebView getWebView() {
        return mIsWebViewAvailable ? mWebView : null;
    }

}
