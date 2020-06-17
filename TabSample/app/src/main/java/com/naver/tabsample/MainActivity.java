package com.naver.tabsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabHost=getTabHost();

        TabHost.TabSpec tabSpecSong=tabHost.newTabSpec("Song").setIndicator("음악별");
        tabSpecSong.setContent(R.id.tabSong);//탭에 화면을 연결
        tabHost.addTab(tabSpecSong);//탭호스트에 탭을 추가

        TabHost.TabSpec tabSpecArtist=tabHost.newTabSpec("ARTIST").setIndicator("가수별");
        tabSpecArtist.setContent(R.id.tabArtist);//탭에 화면을 연결
        tabHost.addTab(tabSpecArtist);//탭호스트에 탭을 추가

        TabHost.TabSpec tabSpecAlbum=tabHost.newTabSpec("ALBUM").setIndicator("앨범별");
        tabSpecAlbum.setContent(R.id.tabAlbum);//탭에 화면을 연결
        tabHost.addTab(tabSpecAlbum);//탭호스트에 탭을 추가
    }
}