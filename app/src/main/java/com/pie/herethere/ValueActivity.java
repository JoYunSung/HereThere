package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pie.herethere.App.AppKey;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ValueActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView value_back, value_img;
    TextView value_toolbar;

    String contentId;

    boolean isBookMarkOk = false;
    ImageView bookMark;

    TextView value_a, value_b;
    ImageView naver;

    Document document;
    AppKey appKey;

    public void Declaration() {
        value_back = (ImageView) findViewById(R.id.value_back);
        value_back.setOnClickListener(this);

        value_img = (ImageView) findViewById(R.id.value_img);
        bookMark = (ImageView) findViewById(R.id.value_book);

        value_toolbar = (TextView) findViewById(R.id.value_toolbar);

        value_a = (TextView) findViewById(R.id.value_a);
        value_b = (TextView) findViewById(R.id.value_b);

        naver = (ImageView) findViewById(R.id.naver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_value);
        Declaration();
        appKey = new AppKey();

        Intent intent = getIntent();

        final String title = intent.getStringExtra("title");
        final String img = intent.getStringExtra("img");

        value_toolbar.setText(title);
        Glide
                .with(getApplicationContext())
                .load(img)
                .into(value_img);

        contentId = intent.getStringExtra("id");

        try {
            FileInputStream fis = openFileInput("Book " + contentId + " .txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));

            String str = br.readLine();

            if (str.equals(title)) {
                isBookMarkOk = true;
            }

            br.close();
            fis.close();
        } catch (Exception e) { }

        bookMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str;
                if (isBookMarkOk) {
                    // 북마크가 되어있을 시
                    str = "해제";
                    isBookMarkOk = false;
                } else {
                    // 북마크가 되어있지않을 시
                    str = title;
                    isBookMarkOk = true;
                }
                try {
                    FileOutputStream fo = openFileOutput("Book " + contentId + " .txt", Context.MODE_PRIVATE);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fo, "UTF-8"));

                    bw.write(str + "\n" + img);
                    bw.close();
                    fo.flush();
                    fo.close();

                    Toast.makeText(getApplicationContext(), "북마크 " + str, Toast.LENGTH_SHORT).show();
                } catch (Exception e) { }
            }
        });

        naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent naverG = new Intent(Intent.ACTION_VIEW, Uri.parse("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=1&ie=utf8&query=" + title));
                startActivity(naverG);
            }
        });

        String url = appKey.getAppURL() + "detailCommon?ServiceKey=" + appKey.getAppKey() + "&contentTypeId=&contentId=" + contentId +
                "&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&defaultYN=Y&firstImageYN=Y&areacodeYN=Y&catcodeYN=Y&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&transGuideYN=Y";

        GetXml getXml = new GetXml();
        getXml.execute(url);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.value_back) {
            finish();
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
            NodeList nodeList = document.getElementsByTagName("item");

            for (int i = 0; i < nodeList.getLength(); i++) {
                try {
                    Node node = nodeList.item(i);
                    Element fstElmnt = (Element) node;

                    NodeList addrList  = fstElmnt.getElementsByTagName("addr1");
                    Element addrElement = (Element) addrList.item(0);
                    addrList = addrElement.getChildNodes();

                    NodeList ovList = fstElmnt.getElementsByTagName("overview");
                    Element ovElement = (Element) ovList.item(0);
                    ovList = ovElement.getChildNodes();

                    String Ov = ovList.item(0).getNodeValue().toString();
                    String Addr = addrList.item(0).getNodeValue().toString();

                    value_a.setText("설명 : " + Ov);
                    value_b.setText("주소 : " + Addr);

                } catch (Exception e) {}
            }
            super.onPostExecute(document);
        }
    }
}