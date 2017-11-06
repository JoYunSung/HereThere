package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pie.herethere.App.AppKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    AppKey app;
    URL FileValue, LoadValue;

    InputMethodManager imm;

    ListView listView;
    Result_ListAdapter adapter;

    ArrayList<Result_ListData> list = new ArrayList<>();
    ArrayList<Result_ListData> newList = new ArrayList<>();

    Document document, document2;

    String result_text;

    TextView tv_search;

    ImageView bar_bt1, bar_bt3, bar_bt4;
    boolean isOk = true;

    public void Declaration() {
        tv_search = (TextView)findViewById(R.id.result_searchText);

        listView = (ListView) findViewById(R.id.result_list);
        listView.setDivider(null);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        bar_bt1 = (ImageView) findViewById(R.id.bar_bt1);
        bar_bt1.setOnClickListener(this);
        bar_bt3 = (ImageView) findViewById(R.id.bar_bt3);
        bar_bt3.setOnClickListener(this);
        bar_bt4 = (ImageView) findViewById(R.id.bar_bt4);
        bar_bt4.setOnClickListener(this);
    }

    public void Ready() {
        try {
            listView.setVisibility(View.INVISIBLE);
            list.clear();

            FileValue = new URL(app.getAppURL() + "searchKeyword?ServiceKey=" + app.getAppKey() + "&keyword=" + URLEncoder.encode(result_text, "utf-8") +
                    "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=10000&pageNo=1");

            GetXml getXml = new GetXml();
            getXml.execute(String.valueOf(FileValue));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        boolean ok = true;
                        for (int j = 0; j < i; j++) {
                            try {
                                if (list.get(i).getContentId().equals(newList.get(j).contentId)) {
                                    ok = false;
                                }
                            } catch (Exception e) { }
                        }
                        if (ok == true) {
                            newList.add(list.get(i));
                        }
                    }
                    adapter = new Result_ListAdapter(getLayoutInflater(), newList);
                    listView.setAdapter(adapter);
                    listView.setVisibility(View.VISIBLE);
                }
            }, 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        app = new AppKey();
        Declaration();

        result_text = getIntent().getStringExtra("inputData");

        tv_search.setText(result_text);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ResultActivity.this, ValueActivity.class);

                intent.putExtra("title", newList.get(i).getTitle());
                intent.putExtra("img", newList.get(i).getImg());
                intent.putExtra("id", newList.get(i).getContentId());

                startActivity(intent);
                overridePendingTransition(R.anim.anim_right, R.anim.anim_hold);
            }
        });

        Ready();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bar_bt1) {
            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt3) {
            Intent intent = new Intent(ResultActivity.this, BookMarkActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }

        if (view.getId() == R.id.bar_bt4) {
            Intent intent = new Intent(ResultActivity.this, SettingsActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    public void onSearchBar(View view) {
        Intent goSearch = new Intent(this,SearchActivity.class);
        goSearch.putExtra("userData", result_text);
        startActivity(goSearch);
        finish();
    }

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
            double lat, lon;
            try {
                NodeList nodeList = document.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList Cat2List  = fstElmnt.getElementsByTagName("cat2");
                    Element Cat2Element = (Element) Cat2List.item(0);
                    Cat2List = Cat2Element.getChildNodes();

                    String cat2 = Cat2List.item(0).getNodeValue().toString();
                    if (cat2.equals("A0101") || cat2.equals("A0102") ||
                        cat2.equals("A0201") || cat2.equals("A0202") || cat2.equals("A0203") ||
                        cat2.equals("A0205") || cat2.equals("A0206")) {

                        if (isOk) {
                            NodeList xList  = fstElmnt.getElementsByTagName("mapx");
                            Element xElement = (Element) xList.item(0);
                            xList = xElement.getChildNodes();

                            NodeList yList = fstElmnt.getElementsByTagName("mapy");
                            Element yElement = (Element) yList.item(0);
                            yList = yElement.getChildNodes();

                            lon = Double.parseDouble(xList.item(0).getNodeValue().toString());
                            lat = Double.parseDouble(yList.item(0).getNodeValue().toString());

                            GetLoad getLoad = new GetLoad();
                            getLoad.execute(app.getAppURL() + "locationBasedList?ServiceKey=" + app.getAppKey()+ "&contentTypeId=&mapX=" + lon + "&mapY=" + lat +
                                    "&radius=2000&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=1000&pageNo=1");
                            isOk = false;
                        }
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

                        list.add(new Result_ListData(title, img, contentId));
                    }
                }
                super.onPostExecute(document);
            }
            catch (Exception e) { }
        }
    }

    private class GetLoad extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                document2 = documentBuilder.parse(new InputSource(url.openStream()));
                document2.getDocumentElement().normalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return document2;
        }

        @Override
        protected void onPostExecute(Document document) {
            try {
                NodeList nodeList = document.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList Cat2List  = fstElmnt.getElementsByTagName("cat2");
                    Element Cat2Element = (Element) Cat2List.item(0);
                    Cat2List = Cat2Element.getChildNodes();

                    String cat2 = Cat2List.item(0).getNodeValue().toString();
                    if (cat2.equals("A0101") || cat2.equals("A0102") ||
                            cat2.equals("A0201") || cat2.equals("A0202") || cat2.equals("A0203") ||
                            cat2.equals("A0205") || cat2.equals("A0206")) {

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

                        list.add(new Result_ListData(title, img, contentId));
                    }
                }
                super.onPostExecute(document);
            }
            catch (Exception e) { }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}