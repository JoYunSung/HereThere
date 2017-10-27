package com.pie.herethere;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    AppKey app;
    URL FileValue;

    ListView listView;
    Search_ListAdapter adapter;

    ArrayList<Search_ListData> list = new ArrayList<>();
    ImageView bt_img;
    EditText editText;

    Document document;

    String search_text, cat;

    LinearLayout search_cl_li;
    ImageView search_cl_1, search_cl_2, search_cl_3;

    int choice = 1;

    public void Declaration() {
        listView = (ListView) findViewById(R.id.search_list);
        bt_img = (ImageView) findViewById(R.id.search_bt_img);
        editText = (EditText) findViewById(R.id.search_editText);

        search_cl_li = (LinearLayout) findViewById(R.id.search_cl_li);
        search_cl_1 = (ImageView) findViewById(R.id.search_cl_1);
        search_cl_2 = (ImageView) findViewById(R.id.search_cl_2);
        search_cl_3 = (ImageView) findViewById(R.id.search_cl_3);

        search_cl_1.setOnClickListener(this);
        search_cl_2.setOnClickListener(this);
        search_cl_3.setOnClickListener(this);

    }

    public void Ready() {
        try {
            listView.setVisibility(View.INVISIBLE);
            list.clear();

            search_text = editText.getText().toString();

            switch (choice) {
                case 1 :
                    cat = "A01";
                    break;
                case 2 :
                    cat = "A02";
                    break;
                case 3 :
                    cat = "A05";
                    break;
            }

            FileValue = new URL(app.getAppURL() + "searchKeyword?ServiceKey=" + app.getAppKey() + "&keyword=" + URLEncoder.encode(search_text, "utf-8") +
                    "&areaCode=&sigunguCode=&cat1=" + cat + "&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=10000&pageNo=1");

            GetXml getXml = new GetXml();
            getXml.execute(String.valueOf(FileValue));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter = new Search_ListAdapter(getLayoutInflater(), list);
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
        setContentView(R.layout.activity_search);
        app = new AppKey();
        Declaration();

        bt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ready();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    Ready();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_cl_1) {
            choice = 1;
        }

        else if (view.getId() == R.id.search_cl_2) {
            choice = 2;
        }

        else if (view.getId() == R.id.search_cl_3) {
            choice = 3;
        }
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
            try {
                NodeList nodeList = document.getElementsByTagName("item");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList Cat1List  = fstElmnt.getElementsByTagName("cat1");
                    Element Cat1Element = (Element) Cat1List.item(0);
                    Cat1List = Cat1Element.getChildNodes();

                    String cat1 = Cat1List.item(0).getNodeValue().toString();

                    if (cat1.equals("A01") || cat1.equals("A02") || cat1.equals("A05")) {
                        NodeList Cat2List  = fstElmnt.getElementsByTagName("cat2");
                        Element Cat2Element = (Element) Cat2List.item(0);
                        Cat2List = Cat2Element.getChildNodes();

                        String cat2 = Cat2List.item(0).getNodeValue().toString();
                        if ((cat2.equals("A0201") || cat2.equals("A0202") || cat2.equals("A0203") ||
                            cat2.equals("A0205") || cat2.equals("A0206")) || (cat1.equals("A01") || cat1.equals("A05"))) {
                            NodeList TitleList  = fstElmnt.getElementsByTagName("title");
                            Element TitleElement = (Element) TitleList.item(0);
                            TitleList = TitleElement.getChildNodes();

                            NodeList ImgList = fstElmnt.getElementsByTagName("firstimage");
                            Element ImgElement = (Element) ImgList.item(0);
                            ImgList = ImgElement.getChildNodes();

                            String img = ImgList.item(0).getNodeValue().toString();
                            String title = TitleList.item(0).getNodeValue().toString();

                            list.add(new Search_ListData(title, img));
                        }
                    }
                }
                super.onPostExecute(document);
            }
            catch (Exception e) {}
        }
    }
}