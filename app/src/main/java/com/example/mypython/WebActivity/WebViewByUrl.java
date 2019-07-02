package com.example.mypython.WebActivity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.mypython.R;

public class WebViewByUrl extends Activity {
    private WebView webView;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_main);
        webView=findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        bundle=this.getIntent().getExtras();
        String url = bundle.getString("url");
        webView.loadUrl(url);
    }
}
