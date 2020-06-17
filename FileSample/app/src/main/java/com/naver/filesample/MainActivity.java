package com.naver.filesample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    EditText edtRaw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sdcard 쓰기권한에 대해서 유저에게 요청
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MODE_PRIVATE);

        edtRaw = (EditText)findViewById(R.id.editText);
        Button btnWriter=(Button)findViewById(R.id.button);

        btnWriter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileOutputStream outFs=openFileOutput("file.txt", Context.MODE_PRIVATE);
                    String str="쿡북 안드로이드";
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(),"file.txt가 생성됨",Toast.LENGTH_LONG).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button btnRead=(Button)findViewById(R.id.button2);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FileInputStream inFs=openFileInput("file.txt");
                    byte[] txt= new byte[30];
                    inFs.read(txt);
                    String str = new String(txt);
                    Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
                    inFs.close();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "파일없음",Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        Button btnRaw = (Button)findViewById(R.id.button3);
        btnRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    InputStream inputS=getResources().openRawResource(R.raw.file);
                    byte[] txt = new byte[inputS.available()];
                    inputS.read(txt );

                    edtRaw.setText(new String(txt));
                    inputS.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        Button button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.d("sdcard",Environment.getExternalStorageDirectory().getAbsolutePath());

                   /* FileInputStream inFs=openFileInput("/storage/emulated/0/file.txt");*/
                    String sdcardPath=Environment.getExternalStorageDirectory().getAbsolutePath();
                    FileInputStream inFs=new FileInputStream(sdcardPath+"/file.txt");
                    byte[] txt= new byte[inFs.available()];
                    inFs.read(txt);
                    String str = new String(txt);
                    edtRaw.setText(str);
                    inFs.close();
                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), "파일없음",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}