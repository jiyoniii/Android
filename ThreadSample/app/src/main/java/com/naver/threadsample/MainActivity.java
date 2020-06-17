package com.naver.threadsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv1, tv2;
    SeekBar pb1, pb2;
    Button btn, btn2;

    //Thread처리 Class
    class MyAsyncTask extends AsyncTask<String, String, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            //주작업은 여기에 코딩
            Log.d("=============","doInBackground()실행됨:");

            for(int i=0;i<=100;i++) {
                //onProgressUpdate호출
                publishProgress(Integer.toString(2*i), Integer.toString(i));
                SystemClock.sleep(100);//0.1초 지연
            }
            return null;
        }
        protected void onProgressUpdate(String... progress) {
            pb1.setProgress(Integer.parseInt(progress[0]));
            tv1.setText("1번진행률:"+pb1.getProgress()+"%");

            pb2.setProgress(Integer.parseInt(progress[1]));
            tv2.setText("2번진행률:"+pb2.getProgress()+"%");
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1=(TextView)findViewById(R.id.textView);
        tv2=(TextView)findViewById(R.id.textView2);
        btn=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button2);
        pb1=(SeekBar)findViewById(R.id.seekBar);
        pb2=(SeekBar)findViewById(R.id.seekBar2);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        for (int i = pb1.getProgress(); i < 100; i = i + 2) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //뷰를 변경하는 코드는 이곳에 작성.
                                    pb1.setProgress(pb1.getProgress() + 2);
                                    tv1.setText("1번진행률 : " + pb1.getProgress() + "%");

                                }
                            });
                            SystemClock.sleep(100); //0.1초 지연
                        }
                    }
                }.start();

                new Thread() {
                    @Override
                    public void run() {
                        for (int i = pb2.getProgress(); i < 100; i++) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //view를 변경하는 코드는 여기에
                                    pb2.setProgress(pb2.getProgress() + 1);
                                    tv2.setText("2번진행률 : " + pb2.getProgress() + "%");
                                }
                            });
                            SystemClock.sleep(100); //0.1초 지연
                        }
                    }

                }.start();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });
    }
}