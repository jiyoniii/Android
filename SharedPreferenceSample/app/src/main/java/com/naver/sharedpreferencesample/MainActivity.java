package com.naver.sharedpreferencesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {

    Button button = null;
    CheckBox checkBox = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        checkBox=(CheckBox)findViewById(R.id.checkBox);

        setContentView(R.layout.activity_main);

        //자동로그인 여부 확인. 두번째 화면으로 넘어가기.
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String autoLogin = mPref.getString("AutoLogin",null);
        if(autoLogin!=null && autoLogin.equals("OK")){
            Intent intent=new Intent(getBaseContext(),Second.class);
            startActivity(intent);
            finish();
            return;
        }

        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인 인증작업

                if(checkBox.isChecked()){
                    //선언
                    SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    //저장
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("AutoLogin","OK");
                    editor.commit();
                }
                Intent intent = new Intent(getBaseContext(),Second.class);
                startActivity(intent);
                finish(); //finish하면 main Activity가 종료됨. (main을 다시 실행 할 수밖에 없음)
            }
        });
    }
}