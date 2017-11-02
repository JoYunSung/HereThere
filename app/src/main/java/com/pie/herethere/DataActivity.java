package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

public class DataActivity extends AppCompatActivity implements View.OnClickListener {

    URL FileValue;

    AppKey app;
    Document document;

    ArrayList<Data_ListData> list = new ArrayList<>();

    ImageView im_backbt;

    ListView listView;
    Data_ListAdapter adapter;

    LocationManager locationManager;
    double userLongitude, userLatitude;
    boolean isReady = false;

    int typeId = 0;

    public void Declaration() {
        app = new AppKey();

        listView = (ListView) findViewById(R.id.data_list);
        listView.setDivider(null);

        im_backbt = (ImageView) findViewById(R.id.data_back);
        im_backbt.setOnClickListener(this);
    }

    public void Ready() {
        try {
            list.clear();

            Intent intent = getIntent();
            String type = intent.getStringExtra("type");

            switch (type) {
                case "where" :
                    typeId = 12;
                    break;
                case "eat" :
                    typeId = 39;
                    break;
            }
            FileValue = new URL(app.getAppURL() + "locationBasedList?ServiceKey=" + app.getAppKey()+ "&contentTypeId=" + typeId + "&mapX=" + userLongitude + "&mapY=" + userLatitude +
                    "&radius=20000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=1000&pageNo=1");

            GetXml getXml = new GetXml();
            getXml.execute(String.valueOf(FileValue));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter = new Data_ListAdapter(getLayoutInflater(), list);
                    listView.setAdapter(adapter);
                }
            }, 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SetFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Declaration();
        SetFont();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        initLocationManager(locationManager);

        final SwipeRefreshLayout refresh = (SwipeRefreshLayout) findViewById(R.id.data_swipe);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(DataActivity.this, ValueActivity.class);

                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("img", list.get(i).getImg());
                intent.putExtra("id", list.get(i).getContentId());

                startActivity(intent);
                overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.data_back) {
            finish();
            overridePendingTransition(0, R.anim.anim_left);
        }
    }

    private void initLocationManager(LocationManager locationManager) {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                    10000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                    10000, // 통지사이의 최소 시간간격 (miliSecond)
                    1, // 통지사이의 최소 변경거리 (m)
                    mLocationListener);
        } catch(SecurityException ex) {}
    }

    private final LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            userLongitude = location.getLongitude(); //경도
            userLatitude = location.getLatitude();   //위도

            if (!isReady) {
                Ready();
                isReady = true;
            }
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider)  {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
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

                    NodeList conList = fstElmnt.getElementsByTagName("contentid");
                    Element conElement = (Element) conList.item(0);
                    conList = conElement.getChildNodes();

                    String contentId = conList.item(0).getNodeValue().toString();

                    list.add(new Data_ListData(title, img, contentId));
                } catch (Exception e) { }
            }
            super.onPostExecute(document);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(mLocationListener);
        Log.d("AnyWhereActivity", "GPS Searching Stop!");
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.anim_left);
    }
}