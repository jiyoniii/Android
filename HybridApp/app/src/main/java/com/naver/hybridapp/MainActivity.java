package com.naver.hybridapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
//
//    EditText edtUrl;
//    Button btnGo, btnBack;
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        edtUrl=(EditText)findViewById(R.id.edtUrl);
//        btnGo=(Button)findViewById(R.id.btnGo);
//        btnBack=(Button)findViewById(R.id.btnBack);
        web=(WebView)findViewById(R.id.webView1);

        WebSettings ws=web.getSettings();
        ws.setJavaScriptEnabled(true);

        web.loadUrl("http://devjy.dothome.co.kr/movie");


        //WebViewClient. 링크를 클릭했을 때 내장브라우저가 아닌 웹뷰에서 페이지가 이동하기위해 필요
        class CookWebViewClient extends WebViewClient{
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
        web.setWebViewClient(new CookWebViewClient());  //웹뷰 클라이언트 세팅

//        btnGo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                web.loadUrl(edtUrl.getText().toString());
//            }
//        });
//
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                web.goBack();
//            }
//        });


    }
}