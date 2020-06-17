package com.naver.dbsample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myHelper;
    Button btnInit, btnInsert, btnSelect;
    EditText edtName, edtNumber;
    ListView listView;
    SQLiteDatabase sqlDB;
    ArrayList<String> arrayList;

    public class MyDBHelper extends SQLiteOpenHelper{

        public MyDBHelper(Context context){
            //groupDB라는 db를 생성함.
            super(context,"groupDB",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블 생성
            db.execSQL("create table groupTBL(gName char(10) primary key,gNumber integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //데이터베이스를 삭제하고, 테이블을 다시 생성. 초기화 한단 뜻.
            //초기화 버튼 누르면 onUpgrade를 호출해서 초기화 시킴.
            //실제론 잘 사용 안하는 코드.
            db.execSQL("drop table if exists groupTBL");
            onCreate(db);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //뷰찾아오기
        edtName=(EditText)findViewById(R.id.edtName);
        edtNumber=(EditText)findViewById(R.id.edtNumber);
        listView=(ListView)findViewById(R.id.listView);

        //MyDBHelper 생성 => DB와 table이 생성됨.
        myHelper=new MyDBHelper(this);

        btnInsert=(Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //쓰기모드로 DB오픈
                sqlDB=myHelper.getWritableDatabase();
                //insert 실행
                sqlDB.execSQL("insert into groupTBL values('"+edtName.getText().toString()+"',"+edtNumber.getText().toString()+")");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"입력완료",Toast.LENGTH_LONG).show();
            }
        });

        btnSelect=(Button)findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //읽기모드로 DB오픈
                sqlDB=myHelper.getReadableDatabase();
                Cursor cursor;
                cursor=sqlDB.rawQuery("select * from groupTBL",null);

                //반복처리
                arrayList=new ArrayList<String>();
                while(cursor.moveToNext()){
                    arrayList.add(cursor.getString(0));
                }
                ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);
                listView.setAdapter(adapter);
                cursor.close();
                sqlDB.close();
            }
        });
        btnInit=(Button)findViewById(R.id.btnInit);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //쓰기모드로 DB오픈
                sqlDB=myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqlDB,1,2);
                sqlDB.close();
            }
        });
        //목록 item을 클릭하면 상세보기화면으로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("가수이름",arrayList.get(position));
                Intent intent=new Intent(getApplicationContext(),Second.class);
                intent.putExtra("name",arrayList.get(position));
                //second activity로 이동
                startActivity(intent);

            }
        });

    }
}