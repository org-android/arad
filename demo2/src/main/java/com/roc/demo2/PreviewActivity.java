package com.roc.demo2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        final String dataStr = getIntent().getStringExtra("diarys");
//        final String dataStr = "<img src=\"file:/storage/emulated/0/QQBrowser/图片收藏/4a05d9c0e68dff8da56cad581189efd4.jpg\" alt=\"image\" width=\" 100%\" height=\" NaN \"><img src=\"/storage/emulated/0/quxiang/趣享GIF/20190222150026.gif\" alt=\"image\" width=\" 100%\" height=\" NaN \">";
        initWebView(dataStr);
    }

    public void initWebView(String data) {
        WebView mWebView = findViewById(R.id.webView);
        WebSettings settings = mWebView.getSettings();

        //settings.setUseWideViewPort(true);//调整到适合webview的大小，不过尽量不要用，有些手机有问题
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        mWebView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        mWebView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.NORMAL);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //设置WebView属性，能够执行Javascript脚本
        mWebView.getSettings().setJavaScriptEnabled(true);//设置js可用
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(new AndroidJavaScript(getApplication()), "android");//设置js接口
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局


/******  22222222  ***********************************************************************/
        data = "</Div><head><style>img{ width:100% !important;}</style></head>" + data;//给图片设置一个样式，宽满屏
/******  2222222222  ***********************************************************************/

        mWebView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }
    /**
     * AndroidJavaScript
     * 本地与h5页面交互的js类，这里写成内部类了
     * returnAndroid方法上@JavascriptInterface一定不能漏了
     */
    private class AndroidJavaScript {
        Context mContxt;

        public AndroidJavaScript(Context mContxt) {
            this.mContxt = mContxt;
        }

        @JavascriptInterface
        public void returnAndroid(String name) {//从网页跳回到APP，这个方法已经在上面的HTML中写上了
            if (name.isEmpty() || name.equals("")) {
                return;
            }
            Toast.makeText(getApplication(), name, Toast.LENGTH_SHORT).show();
            //这里写你的操作///////////////////////
            //MainActivity就是一个空页面，不影响
            Intent intent = new Intent(PreviewActivity.this, MainActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }
}
