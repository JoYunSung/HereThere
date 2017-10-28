package com.pie.herethere;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.pie.herethere.App.AppKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AnyWhereActivity extends AppCompatActivity {

    URL FileValue;

    AppKey app;
    Document document;

    ArrayList<AniWhere_ListData> list = new ArrayList<>();

    ListView listView;
    AniWhere_ListAdapter adapter;

    LocationManager locationManager;
    double userLongitude;
    double userLatitude;

    boolean isReady = false;

    public void Ready() {
        try {
            list.clear();

            FileValue = new URL(app.getAppURL() + "locationBasedList?ServiceKey=" + app.getAppKey()+ "&contentTypeId=12&mapX=" + userLongitude + "&mapY=" + userLatitude +
                    "&radius=20000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=1000&pageNo=1");

            GetXml getXml = new GetXml();
            getXml.execute(String.valueOf(FileValue));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter = new AniWhere_ListAdapter(getLayoutInflater(), list);
                    listView.setAdapter(adapter);
                }
            }, 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_where);

        app = new AppKey();
        listView = (ListView) findViewById(R.id.aniwhere_list);
        listView.setDivider(null);

        //위치 정보 초기화
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initLocationManager(locationManager);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) findViewById(R.id.aniwhere_swipe);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.setVisibility(View.INVISIBLE);
                    }
                }, 100);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Ready();

                        refresh.setRefreshing(false);
                        listView.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        });
    }

    private void initLocationManager(LocationManager locationManager) {
        try{
            // GPS 제공자의 정보가 바뀌면 콜백하도록 리스너 등록하기~!!!
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    10000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    10000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        }catch(SecurityException ex){
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            userLongitude = location.getLongitude(); //경도
            userLatitude = location.getLatitude();   //위도
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.

            if (!isReady) {
                Ready();
                isReady = true;
            }
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }
    };

    private class GetXml extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document = documentBuilder.parse(new InputSource(url.openStream()));
                document.getDocumentElement().normalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return document;
        }

        @Override
        protected void onPostExecute(Document document) {
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                try {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList TitleList  = fstElmnt.getElementsByTagName("title");
                    Element TitleElement = (Element) TitleList.item(0);
                    TitleList = TitleElement.getChildNodes();

                    NodeList ImgList = fstElmnt.getElementsByTagName("firstimage");
                    Element ImgElement = (Element) ImgList.item(0);
                    ImgList = ImgElement.getChildNodes();

                    String img = ImgList.item(0).getNodeValue().toString();
                    String title = TitleList.item(0).getNodeValue().toString();

                    list.add(new AniWhere_ListData(title, img));
                } catch (Exception e) {}
            }
            super.onPostExecute(document);
        }
    }

    private double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(mLocationListener);  //  미수신할때는 반드시 자원해체를 해주어야 한다.
        Log.d("AnyWhereActivity", "GPS Searching Stop!");
    }

    @Override
    protected void attachBaseContext (Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}