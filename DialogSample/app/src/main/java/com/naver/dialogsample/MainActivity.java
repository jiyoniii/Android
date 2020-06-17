package com.naver.dialogsample;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    View dialogView; //다이얼로그에 사용할 커스텀뷰
    EditText dlgEdtNam, dlgEdtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("알림");
                dlg.setMessage("삭제되었습니다.");
                dlg.setIcon(R.drawable.icon01);
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] versionArray=new String[]{"롤리팝","마시멜로","누가"};
                AlertDialog.Builder dlg=new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("좋아하는 버전은?");
                dlg.setIcon(R.drawable.icon01);
                dlg.setSingleChoiceItems(versionArray, 0, new DialogInterface.OnClickListener() {
                    @Override
                    //which는 선택된 item의 index를 뜻함.
                    public void onClick(DialogInterface dialog, int which) {
                        //위에 button1에 final을 붙여주면 button1을 여기서도 사용 가능하게 됨.
                        button1.setText(versionArray[which]);
                    }
                });
                dlg.setPositiveButton("확인",null);
                dlg.show();
            }
        });

        Button button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //custom view 객체 생성
                dialogView=(View)View.inflate(MainActivity.this,R.layout.dailog1,null);
                //custom view를 다이얼로그에 적용
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("사용자 정보 입력");
                dlg.setIcon(R.drawable.icon01);
                dlg.setView(dialogView);//custom view를 다이얼로그에 적용
                dlg.setPositiveButton("확인",null);
                dlg.setNegativeButton("취소",null);
                dlg.show();
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dlgEdtNam=(EditText)dialogView.findViewById(R.id.dlgEdt1);
                        dlgEdtEmail=(EditText)dialogView.findViewById(R.id.dlgEdt2);
                        Log.d("name",dlgEdtNam.getText().toString());
                        Log.d("email",dlgEdtEmail.getText().toString());
                    }
                });


            }
        });
    }
}