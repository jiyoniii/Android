package com.naver.mysmoothie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    CheckBox checkBox1;
    RadioGroup radioGroup1;
    RadioButton radioButton1, radioButton2, radioButton3;
    Button button1;
    ImageView imageView1;
    TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox1=(CheckBox)findViewById(R.id.checkbox01);
        textView1=(TextView)findViewById(R.id.textView02);
        radioGroup1=(RadioGroup)findViewById(R.id.radiogroup01);
        radioButton1=(RadioButton)findViewById(R.id.radioButton01);
        radioButton2=(RadioButton)findViewById(R.id.radioButton02);
        radioButton3=(RadioButton)findViewById(R.id.radioButton03);
        button1=(Button)findViewById(R.id.button01);
        imageView1=(ImageView)findViewById(R.id.imageView01);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkBox1.isChecked()){
                    radioGroup1.setVisibility(View.VISIBLE);
                    textView1.setVisibility(View.VISIBLE);
                    button1.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.VISIBLE);
                }else{
                    radioGroup1.setVisibility(View.INVISIBLE);
                    button1.setVisibility(View.INVISIBLE);
                    imageView1.setVisibility(View.INVISIBLE);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (radioGroup1.getCheckedRadioButtonId()){
                    case R.id.radioButton01:
                        imageView1.setImageResource(R.drawable.strawberry);
                        break;
                    case R.id.radioButton02:
                        imageView1.setImageResource(R.drawable.vanilla);
                        break;
                    case R.id.radioButton03:
                        imageView1.setImageResource(R.drawable.watermelon);
                        break;
                };
            }
        });


    }
}