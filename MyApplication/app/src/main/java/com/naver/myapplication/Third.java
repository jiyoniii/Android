package com.naver.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Third extends AppCompatActivity {
    Button button1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
        outIntent.putExtra("result","테스트");
        setResult(RESULT_OK,outIntent);//mainactivity로 결과를 보냄.
        finish();

    }
}