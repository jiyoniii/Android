package com.naver.menusample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    LinearLayout baseLayout;
    Button button1;


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemRed:
                Log.d("색상변경","Red");
                baseLayout.setBackgroundColor(Color.RED);
                return  true;
            case R.id.itemGreen:
                Log.d("색상변경","Green");
                baseLayout.setBackgroundColor(Color.GREEN);
                return true;
            case R.id.itemBlue:
                Log.d("색상변경","Blue");
                baseLayout.setBackgroundColor(Color.BLUE);
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseLayout=(LinearLayout)findViewById(R.id.baseLayout);
        button1=(Button)findViewById(R.id.button1);
        registerForContextMenu(button1);    //버튼에 컨텍스트메뉴 등록

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater mInflater=getMenuInflater();
        if(v==button1){
            menu.setHeaderTitle("배경색 변경");  //context메뉴의 title
            mInflater.inflate(R.menu.menu1,menu);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater mInflater=getMenuInflater();
        mInflater.inflate(R.menu.menu1,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.itemRed:
                Log.d("색상변경","Red");
                baseLayout.setBackgroundColor(Color.RED);
                return  true;
            case R.id.itemGreen:
                Log.d("색상변경","Green");
                baseLayout.setBackgroundColor(Color.GREEN);
                return true;
            case R.id.itemBlue:
                Log.d("색상변경","Blue");
                baseLayout.setBackgroundColor(Color.BLUE);
                return true;
        }
        return false;
    }
}