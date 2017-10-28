package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

public class SearchActivity extends AppCompatActivity {

    AppKey app;
    URL FileValue;

    InputMethodManager imm;

    ListView listView;
    Search_ListAdapter adapter;

    ArrayList<Search_ListData> list = new ArrayList<>();
    ImageView bt_img;
    EditText editText;

    Document document;

    String search_text, cat;

    int update = 0;

    public void Declaration() {
        listView = (ListView) findViewById(R.id.search_list);
        bt_img = (ImageView) findViewById(R.id.search_bt_img);
        editText = (EditText) findViewById(R.id.search_editText);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public void Ready() {
        final int checkUpdate = update;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (checkUpdate == update) {
                    try {
                        listView.setVisibility(View.INVISIBLE);
                        list.clear();

                        search_text = editText.getText().toString();

                        FileValue = new URL(app.getAppURL() + "searchKeyword?ServiceKey=" + app.getAppKey() + "&keyword=" + URLEncoder.encode(search_text, "utf-8") +
                                "&areaCode=&sigunguCode=&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=10000&pageNo=1");

                        GetXml getXml = new GetXml();
                        getXml.execute(String.valueOf(FileValue));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new Search_ListAdapter(getLayoutInflater(), list);
                                listView.setAdapter(adapter);
                                listView.setVisibility(View.VISIBLE);
                            }
                        }, 500);
                    } catch (Exception e) { }
                }
            }
        }, 500);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        app = new AppKey();
        Declaration();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("jua.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        bt_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                Intent goResult = new Intent(SearchActivity.this, ResultActivity.class);
                goResult.putExtra("inputData", editText.toString());
                startActivity(goResult);
                finish();
            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    Update();
                    Ready();
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this, ValueActivity.class);

                intent.putExtra("title", list.get(i).getTitle());
                intent.putExtra("img", list.get(i).getImg());
                intent.putExtra("id", list.get(i).getContentId());

                startActivity(intent);
            }
        });

        EditText edit = (EditText)findViewById(R.id.search_editText);
        edit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Update();
                Ready();
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                Update();
                Ready();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력하기 전에
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

                        list.add(new Search_ListData(title, img, contentId));
                    }
                }
                super.onPostExecute(document);
            }
            catch (Exception e) {

            }
        }
    }

    @Override
    protected void attachBaseContext (Context newBase){
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void Update() {
        if (update >= 100000)
            update = 0;
        update++;
    }
}