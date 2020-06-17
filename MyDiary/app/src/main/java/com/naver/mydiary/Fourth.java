package com.naver.mydiary;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Fourth extends AppCompatActivity {

    Third.MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    TextView fourth_Date, fourth_Text;
    ImageButton backBtn;
    ImageButton delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        Intent intent = getIntent();

        String dDate = intent.getStringExtra("dDate");
        String dText = intent.getStringExtra("dText");

        Toast.makeText(this,dDate,Toast.LENGTH_LONG).show();


        fourth_Date=(TextView)findViewById(R.id.fourth_Date);
        fourth_Date.setText(dDate);

        fourth_Text=(TextView)findViewById(R.id.fourth_Text);
        Log.d("test",dText);

        fourth_Text.setText(dText);


        backBtn=(ImageButton)findViewById(R.id.backBtn);
        delBtn=(ImageButton)findViewById(R.id.delBtn);




    }
}