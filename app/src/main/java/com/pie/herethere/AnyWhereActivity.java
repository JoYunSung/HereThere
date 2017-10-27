package com.pie.herethere;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

    int Code, PageNo = 1, MaxValue = 5;
    String cat1, cat2;

    AppKey app;
    Document document;

    ArrayList<AniWhere_ListData> list = new ArrayList<>();

    ListView listView;
    AniWhere_ListAdapter adapter;

    public void Ready() {
        try {
            for (int i = 0; i < MaxValue; i++) {
                Code = (int) (Math.random() * 2);

                if (Code == 0) {
                    cat1 = "A01";
                    cat2 = "A0101";

                    PageNo = (int) (Math.random() * 138) + 1;
                } else {
                    cat1 = "A02";
                    cat2 = "A0201";

                    PageNo = (int) (Math.random() * 171) + 1;
                }
                FileValue = new URL(app.getAppURL() + "areaBasedList?ServiceKey=" + app.getAppKey() + "&contentTypeId=&areaCode=&sigunguCode=" +
                        "&cat1=" + cat1 + "&cat2=" + cat2 + "&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=12&pageNo=" + PageNo);

                GetXml getXml = new GetXml();
                getXml.execute(String.valueOf(FileValue));
            }

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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Ready();

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
                        list.clear();
                        Ready();

                        if (list.size() > MaxValue) {
                            for (int i = MaxValue; i < list.size(); i++) {
                                list.remove(i);
                            }
                        }
                        refresh.setRefreshing(false);
                        listView.setVisibility(View.VISIBLE);
                    }
                }, 500);
            }
        });
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
            NodeList nodeList = document.getElementsByTagName("item");
            Node node = nodeList.item((int)(Math.random() * 12));
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
            super.onPostExecute(document);
        }
    }

    @Override
    protected void attachBaseContext (Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}