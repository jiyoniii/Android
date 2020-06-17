package com.naver.customlistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //adapter 생성. adapter 내부에서 Arraylist가 생성됨.
        adapter=new ListViewAdapter();

        //ListView에 adapter 적용
        listView=(ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);

        //ArrayList에 데이터 추가
        // 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon01),
                "홍길동", "프로그래머") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon02),
                "이순신", "웹퍼블리셔") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.icon03),
                "왕건", "사진작가") ;

    }
}