package com.pie.herethere;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.pie.herethere.App.AppKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.R.id.list;

public class SearchActivity extends AppCompatActivity {

    URL FileURL, FileValue;
    Document document, valueDocumanet;
    TextView text_test1;

    ArrayList<Search_ListData>resultList = new ArrayList<>();

    Search_ListAdapter adapter;

    AppKey appkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        appkey = new AppKey();

        text_test1 = (TextView)findViewById(R.id.text_test1);

        int AreaCode = 1;
        int PageNum = 1;

        try {
            FileURL = new URL(appkey.getAppURL() + "areaBasedList?ServiceKey=" + appkey.getAppKey() + "&contentTypeId=25&areaCode=" + AreaCode +
                    "&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=12&pageNo=" + PageNum);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        GetXml getXml = new GetXml();
        getXml.execute(String.valueOf(FileURL));

    }
//가나다
    int number[] = new int[] {0, 1, 2, 3};

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
            for (int i = 0; i < 4; i++) {
                NodeList nodeList = document.getElementsByTagName("item");
                Node node = nodeList.item(number[i]);
                Element fstElmnt = (Element) node;

                NodeList TitleList  = fstElmnt.getElementsByTagName("title");
                Element TitleElement = (Element) TitleList.item(0);
                TitleList = TitleElement.getChildNodes();

                NodeList ImgList = null;
                String img = "이미지 없음";
                try {
                    ImgList = fstElmnt.getElementsByTagName("firstimage");
                    Element ImgElement = (Element) ImgList.item(0);
                    ImgList = ImgElement.getChildNodes();
                    img = ImgList.item(0).getNodeValue().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String title = TitleList.item(0).getNodeValue().toString();

                NodeList IdList  = fstElmnt.getElementsByTagName("contentid");
                Element IdElement = (Element) IdList.item(0);
                IdList = IdElement.getChildNodes();

                try {
                    FileValue = new URL(appkey.getAppURL() + "detailIntro?ServiceKey=" + appkey.getAppKey() + "&contentTypeId=25&contentId=" +
                            IdList.item(0).getNodeValue().toString() + "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                GetValue getValue = new GetValue();

                String km = "";
                String time = "";

                try {
                    NodeList nodeValue = getValue.execute(String.valueOf(FileValue)).get().getDocumentElement().getElementsByTagName("item");
                    Node value = nodeValue.item(0);
                    Element element = (Element) value;

                    NodeList KmList  = element.getElementsByTagName("distance");
                    Element KmElement = (Element) KmList.item(0);
                    KmList = KmElement.getChildNodes();

                    km = KmList.item(0).getNodeValue();

                    NodeList TimeList  = element.getElementsByTagName("taketime");
                    Element TimeElement = (Element) TimeList.item(0);
                    TimeList = TimeElement.getChildNodes();

                    time = TimeList.item(0).getNodeValue();

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"사망", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                resultList.add(new Search_ListData(title, img));
                text_test1.setText(title);
            }
            super.onPostExecute(document);
        }
    }

    private class GetValue extends AsyncTask<String, Void, Document> {

        @Override
        protected Document doInBackground(String... urls) {
            URL url;
            try {
                url = new URL(urls[0]);
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                valueDocumanet = documentBuilder.parse(new InputSource(url.openStream()));
                valueDocumanet.getDocumentElement().normalize();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return valueDocumanet;
        }
    }
}