package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pie.herethere.App.AppKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class WValueActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView BackImage, BackGroundImg;
    TextView ToolbarText;

    Document document;
    String Title, Img, ContentId, lat, lot;

    AppKey appKey = new AppKey();

    ListView ListView;
    WValue_ListAdapter adapter;
    ArrayList<Weather_ListData> listData = new ArrayList<>();

    public void Declaration() {
        BackImage = (ImageView) findViewById(R.id.wvalue_back);
        BackImage.setOnClickListener(this);

        BackGroundImg = (ImageView) findViewById(R.id.wvalue_img);
        ListView = (ListView) findViewById(R.id.wvalue_list);

        ToolbarText = (TextView) findViewById(R.id.wvalue_toolbar);
    }

    public void GetData() {
        Intent intent = getIntent();

        Img = intent.getStringExtra("img");
        Glide
                .with(getApplicationContext())
                .load(Img)
                .into(BackGroundImg);

        Title = intent.getStringExtra("title");
        ToolbarText.setText(Title);

        ContentId = intent.getStringExtra("id");

        try {
            URL url = new URL(appKey.getAppURL() + "searchKeyword?ServiceKey=" + appKey.getAppKey() + "&keyword=" + URLEncoder.encode(Title, "utf-8") +
                    "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=A&numOfRows=10&pageNo=1");

            Getlocation getlocation = new Getlocation();
            getlocation.execute(String.valueOf(url));
        } catch (Exception e) { }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wvalue);
        Declaration();
        SetFont();
        GetData();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wvalue_back) {
            finish();
        }
    }

    private class Getlocation extends AsyncTask<String, Void, Document> {
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
            try {
                NodeList nodeList = document.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList conList = fstElmnt.getElementsByTagName("contentid");
                    Element conElement = (Element) conList.item(0);
                    conList = conElement.getChildNodes();
                    String contentId = conList.item(0).getNodeValue().toString();

                    if (contentId.equals(ContentId)) {
                        NodeList lotList = fstElmnt.getElementsByTagName("mapx");
                        NodeList latList = fstElmnt.getElementsByTagName("mapy");
                        Element lotElement = (Element) lotList.item(0);
                        Element latElement = (Element) latList.item(0);
                        lotList = lotElement.getChildNodes();
                        latList = latElement.getChildNodes();
                        lot = lotList.item(0).getNodeValue().toString();
                        lat = latList.item(0).getNodeValue().toString();

                        Toast.makeText(getApplicationContext(), lot + " : " + lat, Toast.LENGTH_SHORT).show();
                    }
                }
                super.onPostExecute(document);
            }
            catch (Exception e) { }
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}