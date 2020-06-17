package com.naver.graphicsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MyGraphicView(this));
    }

    private static class MyGraphicView extends View {
        public MyGraphicView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            //붓 역할을 할 객체 생성
            Paint paint = new Paint();
            paint.setAntiAlias(true); //외곽선을 부드럽게
            paint.setColor(Color.RED);
            canvas.drawLine(10,100,300,200,paint);

            Rect rect =new Rect(250,50,250+100,50+100);
            canvas.drawRect(rect,paint);

            paint.setColor(Color.BLUE);
            canvas.drawCircle(60,220,50,paint);

            paint.setTextSize(60);
            canvas.drawText("안드로이드",10,390,paint);

            Bitmap picture= BitmapFactory.decodeResource(getResources(),R.drawable.arcade);
            canvas.drawBitmap(picture,10,500,null);
            picture.recycle();
        }
    }
}