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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    AppKey app;
    URL FileValue;

    InputMethodManager imm;

    ListView listView;
    Result_ListAdapter adapter;

    ArrayList<Result_ListData> list = new ArrayList<>();

    Document document;

    String result_text, cat;

    TextView tv_search;

    LinearLayout result_cl_li;
    ImageView result_cl_1, result_cl_2;

    int choice = 1;
    ImageView back;

    public void Declaration() {
        tv_search = (TextView)findViewById(R.id.result_searchText);

        listView = (ListView) findViewById(R.id.result_list);

        result_cl_li = (LinearLayout) findViewById(R.id.result_cl_li);
        result_cl_1 = (ImageView) findViewById(R.id.result_cl_1);
        result_cl_2 = (ImageView) findViewById(R.id.result_cl_2);

        result_cl_1.setOnClickListener(this);
        result_cl_2.setOnClickListener(this);

        back = (ImageView) findViewById(R.id.result_back);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void Ready() {
        try {
            listView.setVisibility(View.INVISIBLE);
            list.clear();

            switch (choice) {
                case 1 :
                    cat = "A01";
                    break;
                case 2 :
                    cat = "A02";
                    break;
            }

            FileValue = new URL(app.getAppURL() + "searchKeyword?ServiceKey=" + app.getAppKey() + "&keyword=" + URLEncoder.encode(result_text, "utf-8") +
                    "&areaCode=&sigunguCode=&cat1=" + cat + "&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=10000&pageNo=1");

            GetXml getXml = new GetXml();
            getXml.execute(String.valueOf(FileValue));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    adapter = new Result_ListAdapter(getLayoutInflater(), list);
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

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ResultActivity.this, ValueActivity.class);

                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("img", list.get(i).getImg());
                intent.putExtra("id", list.get(i).getContentId());

                startActivity(intent);
            }
        });

        Ready();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.result_cl_1) {
            choice = 1;
        }
        else if (view.getId() == R.id.result_cl_2) {
            choice = 2;
        }
        Ready();
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
            catch (Exception e) {}
        }
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}