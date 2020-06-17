package com.naver.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edt1, edt2;
    Button btn1, btn2, btn3, btn4;
    TextView tv1;
    String num1, num2;
    Integer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.edt1=(EditText)findViewById(R.id.editText);
        this.edt2=(EditText)findViewById(R.id.editText2);
        this.btn1=(Button)findViewById(R.id.button01);
        this.btn2=(Button)findViewById(R.id.button02);
        this.btn3=(Button)findViewById(R.id.button03);
        this.btn4=(Button)findViewById(R.id.button04);
        this.tv1=(TextView)findViewById(R.id.textview);


        //덧셈
        this.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=edt1.getText().toString();
                num2=edt2.getText().toString();
                result=Integer.parseInt(num1)+Integer.parseInt(num2);
                tv1.setText("계산결과 : "+result);
            }
        });
    //뺄셈
        this.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=edt1.getText().toString();
                num2=edt2.getText().toString();
                result=Integer.parseInt(num1)-Integer.parseInt(num2);
                tv1.setText("계산결과 : "+result);
            }
        });

//곱하기
        this.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=edt1.getText().toString();
                num2=edt2.getText().toString();
                result=Integer.parseInt(num1)*Integer.parseInt(num2);
                tv1.setText("계산결과 : "+result);
            }
        });

        //나누기
        this.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1=edt1.getText().toString();
                num2=edt2.getText().toString();
                result=Integer.parseInt(num1)/Integer.parseInt(num2);
                tv1.setText("계산결과 : "+result);
            }
        });
    }
}
