package com.naver.mydiary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Second extends AppCompatActivity {

    Third.MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    ArrayList<String> arrayList;
    ImageButton imgBtn;
    ListView list;
    TextView dDate;
    private Context context;

    //oncreate에서는 초기화 작업
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //버튼 누르면 일기쓰기 화면으로 이동
        imgBtn=(ImageButton)findViewById(R.id.imgBtn);
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               Intent를 사용해서 Third클래스로 activity를 넘겨주게 됨.
                Intent intent = new Intent(getApplicationContext(),Third.class);
                startActivity(intent);

            }
        });

        //LISTview에 일기 나오기,  날짜 선택하면 해당 일기로 이동하기.
        list=(ListView) findViewById(R.id.list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),Fourth.class);
                intent.putExtra("dDate",arrayList.get(position));
                //해당 날짜의 본문이 나오게 하려면
                intent.putExtra("dText",arrayList.get(2));
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        myHelper=new Third.MyDBHelper(this);

        sqlDB=myHelper.getReadableDatabase();
        Cursor cursor;
        cursor=sqlDB.rawQuery("select dDate from diaryTBL",null);


        //반복처리
        arrayList=new ArrayList<String>();
        while (cursor.moveToNext()){
            arrayList.add(cursor.getString(0));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
        list.setAdapter(adapter);

        sqlDB.close();
        cursor.close();


    }
}