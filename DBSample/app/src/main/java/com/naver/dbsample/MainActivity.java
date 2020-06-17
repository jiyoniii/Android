package com.naver.dbsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MyDBHelper myHelper;
    Button btnInit, btnInsert, btnSelect;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    SQLiteDatabase sqlDB;

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
        edtNameResult=(EditText)findViewById(R.id.edtNameResult);
        edtNumberResult=(EditText)findViewById(R.id.edtNumberResult);

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
                //컬럼제목
                String strNames="그룹이름"+"\r\n"+"------------------------"+"\r\n";
                String strNumbers="인원"+"\r\n"+"------------------------"+"\r\n";
                //반복처리
                while(cursor.moveToNext()){
                    strNames+=cursor.getString(0)+"\r\n";
                    strNumbers+=cursor.getString(1)+"\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);

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
    }
}