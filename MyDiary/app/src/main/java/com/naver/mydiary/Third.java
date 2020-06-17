package com.naver.mydiary;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
//글쓰기 화면
public class Third extends AppCompatActivity {
    MyDBHelper myHelper;
    TextView dDate;
    TextInputEditText dText;
    Button btnInsert;
    Calendar myCalendar = Calendar.getInstance();
    SQLiteDatabase sqlDB;



    //DB만들고, 테이블 구성하는 class
    public static class MyDBHelper extends SQLiteOpenHelper{

        public MyDBHelper(Context context){
            //diaryDB라는 db를 생성함.
            super(context,"diaryDB_2",null,1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블 생성
            db.execSQL("create table diaryTBL(dDate char(20), dText char(100))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //뷰 찾아오기
        final TextInputEditText dText=(TextInputEditText)findViewById(R.id.dText);

        //MyDBHelper 생성 =>DB와 table이 생성됨
        myHelper=new MyDBHelper(this);

        //textView에 기본으로 오늘날짜 입력하기
        final TextView dDate = (TextView) findViewById(R.id.dDate);
        dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Third.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Button 누르면 sqlite에 등록되는 방식
        btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SQLite에 쓴 글이 등록되고. 되고 난 후에 "등록되었습니다" toast띄우고 2번째 LIST화면으로 되돌아가야함.

                //쓰기모드로 DB오픈
                sqlDB=myHelper.getWritableDatabase();
                Log.d("test",dDate.getText().toString());
                //insert 실행
                sqlDB.execSQL("insert into diaryTBL values('"+dDate.getText().toString()+"','"+dText.getText().toString()+"')");
                sqlDB.close();
                Toast.makeText(getApplicationContext(),"입력완료",Toast.LENGTH_LONG).show();
                //두번째 화면 으로 이동하기 Third를...SecondClass로?
                Intent intent = new Intent(getApplicationContext(),Second.class);

                startActivity(intent);
            }
        });

    }


    //날짜 설정하는 dialog
    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            myCalendar.set(Calendar.YEAR,yy);
            myCalendar.set(Calendar.MONTH,mm);
            myCalendar.set(Calendar.DAY_OF_MONTH,dd);
            updateLabel();
        }

    };
    //calendar dialog에서 찍은 날짜 입력되게
    public void updateLabel(){
        String myFormat = "yyyy / MM / dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

            TextView dDate = (TextView)findViewById(R.id.dDate);
            dDate.setText(sdf.format(myCalendar.getTime()));
        }
    }






