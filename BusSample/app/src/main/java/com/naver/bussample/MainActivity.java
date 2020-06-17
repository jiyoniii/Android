package com.naver.bussample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    private String downLoadUrl(String myurl) throws IOException {

        HttpURLConnection conn = null;
        //서버에 요청해서 다운로드.
        try {
            URL url = new URL(myurl); //서버주소로 URL 객체생성
            conn = (HttpURLConnection) url.openConnection();

            //파일 다운로드, 읽어옴
            BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());

            //UTF로 인코딩
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf,"utf-8"));

            String line = null;
            String page = "";

            while ((line = bufreader.readLine()) != null) { //다운로드한 양이 있으면
                page += line;
            }
           return page;

        } finally {
            conn.disconnect();
        }
    }


    private class DownLoadWebPageTask extends AsyncTask<String,Void,String>{

        @Override
        //데이터 파싱은 여기서 하면됨(onPostExecute)
        protected void onPostExecute(String result) {
            String headerCd = "";
            String gpsX = "";
            String gpsY = "";
            String plainNo = "";

            boolean bSet_headerCd = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;
            boolean bSet_plainNo = false;

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if(eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if (tag_name.equals("gpsX"))
                            bSet_gpsX = true;
                        if (tag_name.equals("gpsY"))
                            bSet_gpsY = true;
                        if (tag_name.equals("plainNo"))
                            bSet_plainNo = true;
                    } else if(eventType == XmlPullParser.TEXT) {
                        if (bSet_headerCd) {
                            headerCd = xpp.getText();
                            tv.append("headerCd: " + headerCd + "\n");
                            bSet_headerCd = false;
                        }

                        if (headerCd.equals("0")) {
                            if (bSet_gpsX) {
                                gpsX = xpp.getText();
                                tv.append("gpsX: " + gpsX + "\n");
                                bSet_gpsX = false;
                            }
                            if (bSet_gpsY) {
                                gpsY = xpp.getText();
                                tv.append("gpsY: " + gpsY + "\n");
                                bSet_gpsY = false;
                            }
                            if (bSet_plainNo) {
                                plainNo = xpp.getText();
                                tv.append("plainNo; " + plainNo + "\n");
                                bSet_plainNo = false;
                            }
                        }
                    } else if(eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    //JDBC의 커서와 비슷한 역할을 하는 next()
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                tv.setText(e.getMessage());
            }

        }


        @Override
        protected String doInBackground(String... urls) {
            try{
                return (String)downLoadUrl((String)urls[0]);
            }catch(IOException e) {
                return "다운로드실패";
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.data);

        String serviceUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";
        String serviceKey = "p9nzbtAhgKarGOpCTxgaFjIgp3%2FSHruC7ZbcdpOSJckjenwwrk%2F%2B39TrfPNVnMDAP8GpumY8qsYTl7TnutlyCQ%3D%3D";
        String busRouteId = "100100462";
        String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&busRouteId="+busRouteId;


//       String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
//       String serviceKey = "p9nzbtAhgKarGOpCTxgaFjIgp3%2FSHruC7ZbcdpOSJckjenwwrk%2F%2B39TrfPNVnMDAP8GpumY8qsYTl7TnutlyCQ%3D%3D";
//       String strSrch = "7715";
//       String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;

       new DownLoadWebPageTask().execute(strUrl);

    }


}