package com.naver.loginsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    EditText editText1, editText2;
    Button button1;
    TextView textView1;
    DownLoadURL myTask;

    class DownLoadURL extends AsyncTask<String, Void, String> {
        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {

                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();

                /* post방식으로 처리 시작 ==========================*/
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                DataOutputStream out = null;
                out = new DataOutputStream(conn.getOutputStream());
                Properties prop = new Properties();
                // 서버에 전달할 값 설정
                prop.setProperty("id", editText1.getText().toString());
                prop.setProperty("pw", editText2.getText().toString());
                String encodedString = encodeString(prop);
                out.writeBytes(encodedString);
                out.flush();
                /* post방식으로 처리 끝 ===============================*/

                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while((line = bufreader.readLine()) != null) {
                    page += line;
                }
                return page;
            } finally {
                conn.disconnect();
            }
        }


        @Override
        protected void onPostExecute(String result) {
            //view를 변경한다. json파싱

            //{"result":[{"msg":"ok"}]} 이런형태로 result값이 저장되어 있음.
            try { //result로 전체 데이터가 넘어옴. 그래서 json이라는 객체를 만듦.
                JSONObject json = new JSONObject(result);

                //result 안에 [] 배열로 값들이 저장되어 있기 떄문에 Array로 만들어져야 함.
                JSONArray jArr = json.getJSONArray("result");

                //for문으로 배열안에 있는 {} 객체들을 구함.
                for (int i=0; i<jArr.length(); i++) {
                    //배열 안에 또 들어있는 {"msg":"ok"}등과 같은 객체들을 구해서 json에 다시 저장. (json을 재활용함)
                    json = jArr.getJSONObject(i);
                    final String msg = json.getString("msg");
                    Log.d("디버깅 msg : ",msg);
                    if(msg.equals("ok")){
                        textView1.setText("로그인 성공");
                        //다음페이지로 이동할 시엔 여기서 Second Activity로 이동해야 함.
                    }else{
                        textView1.setText("로그인 실패");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String)downloadUrl((String)urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }
    }


    //Parameter를 인코딩해서 문자열로 리턴
    public static String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            try{
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value,"utf-8") );
            }catch(Exception e){
                e.printStackTrace();
            }
            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1=(EditText)findViewById(R.id.editText1);
        editText2=(EditText)findViewById(R.id.editText2);
        button1=(Button)findViewById(R.id.button1);
        textView1=(TextView)findViewById(R.id.textView1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 누르면 AsyncTask를 실행시킨다.
                //post방식일 때 url
                String url ="http://10.0.2.2:8181/JSONServer/login.jsp";
                //AsyncTask실행
                myTask=new DownLoadURL();
                myTask.execute(url);

            }
        });
    }
}