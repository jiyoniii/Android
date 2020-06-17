package com.naver.dbsample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class Second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent=getIntent();
        //name으로 보냈으니 받을때도 name으로 받기
        String name = intent.getStringExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_LONG).show();

        //primary key인 name으로 select 해서 view값을 출력

    }
}