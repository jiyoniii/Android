package com.naver.busposition;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //마커 표시하는 코드
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ///// (1) Bus Route ID /////
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";

        String serviceKey = "p9nzbtAhgKarGOpCTxgaFjIgp3%2FSHruC7ZbcdpOSJckjenwwrk%2F%2B39TrfPNVnMDAP8GpumY8qsYTl7TnutlyCQ%3D%3D";
        String strSrch = "571";
        String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;

        DownloadWebpageTask1 task1 = new DownloadWebpageTask1();
        task1.execute(strUrl);


    }

    private class DownloadWebpageTask1 extends AsyncTask<String, Void, String> {

        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while ((line = bufreader.readLine()) != null) {
                    page += line;
                }

                return page;
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return (String) downloadUrl((String) urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        protected void onPostExecute(String result) {
            Log.d("버스ID",result);

            String headerCd = "";
            String busRouteId = "";
            String busRouteNm = "";

            boolean bSet_headerCd = false;
            boolean bSet_busRouteId = false;
            boolean bSet_busRouteNm = false;

            ///// (1) Bus Route ID /////
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_DOCUMENT) {
                        ;
                    } else if (eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if (tag_name.equals("busRouteId"))
                            bSet_busRouteId = true;
                        if (tag_name.equals("busRouteNm"))
                            bSet_busRouteNm = true;
                    } else if (eventType == XmlPullParser.TEXT) {
                        if (bSet_headerCd) {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }

                        if (headerCd.equals("0")) {
                            if (bSet_busRouteId) {
                                busRouteId = xpp.getText();
                                bSet_busRouteId = false;
                            }
                            if (bSet_busRouteNm) {
                                busRouteNm = xpp.getText();
                                bSet_busRouteNm = false;
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG) {
                        ;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }

            ///// (2) Bus Position /////
            String serviceUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";
            String serviceKey = "p9nzbtAhgKarGOpCTxgaFjIgp3%2FSHruC7ZbcdpOSJckjenwwrk%2F%2B39TrfPNVnMDAP8GpumY8qsYTl7TnutlyCQ%3D%3D";
            String strUrl = serviceUrl + "?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;

            DownloadWebpageTask2 task2 = new DownloadWebpageTask2();
            task2.execute(strUrl);
        }
    }
        //task1에서 구한 busrouteID로 gps를 구하기.
        private class DownloadWebpageTask2 extends DownloadWebpageTask1 {

            protected void onPostExecute(String result) {
                Log.d("xml데이터",result);


                String headerCd = "";
                String plainNo = "";
                String gpsX = "";
                String gpsY = "";

                boolean bSet_headerCd = false;
                boolean bSet_gpsX = false;
                boolean bSet_gpsY = false;
                boolean bSet_plainNo = false;

                ///// (2) Bus Positions
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(result));
                    int eventType = xpp.getEventType();

                    int count = 0;
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_DOCUMENT) {
                            ;
                        } else if (eventType == XmlPullParser.START_TAG) {
                            String tag_name = xpp.getName();
                            if (tag_name.equals("headerCd"))
                                bSet_headerCd = true;
                            if (tag_name.equals("gpsX"))
                                bSet_gpsX = true;
                            if (tag_name.equals("gpsY"))
                                bSet_gpsY = true;
                            if (tag_name.equals("plainNo"))
                                bSet_plainNo = true;
                        } else if (eventType == XmlPullParser.TEXT) {
                            if (bSet_headerCd) {
                                headerCd = xpp.getText();
                                bSet_headerCd = false;
                            }

                            if (headerCd.equals("0")) {
                                if (bSet_gpsX) {
                                    count++;

                                    gpsX = xpp.getText();
                                    bSet_gpsX = false;
                                }
                                if (bSet_gpsY) {
                                    gpsY = xpp.getText();
                                    bSet_gpsY = false;
                                }
                                if (bSet_plainNo) {
                                    plainNo = xpp.getText();
                                    bSet_plainNo = false;
                                    //지도에 출력하는 함수displayBus
                                    displayBus(gpsX, gpsY, plainNo);
                                }
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            ;
                        }
                        eventType = xpp.next();
                    }
                } catch (Exception e) {
                }
            }

            //구한 gps로 구글맵에 표시하는 method(marker띄우는 코드)
            private void displayBus(String gpsX, String gpsY, String plainNo) {

                double latitude;
                double longitude;
                LatLng LOC;

                latitude = Double.parseDouble(gpsY);
                longitude = Double.parseDouble(gpsX);
                LOC = new LatLng(latitude, longitude);
                Marker mk1 = mMap.addMarker(new MarkerOptions()
                        .position(LOC)
                        .title(plainNo)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

            }

        }
    }

