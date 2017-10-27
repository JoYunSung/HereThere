package com.pie.herethere;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.pie.herethere.App.AppKey;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SearchActivity extends AppCompatActivity {

    AppKey appKey;

    ListView listView;
    ArrayList<String> datas = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        appKey = new AppKey();

        listView = (ListView)findViewById(R.id.search_list);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,datas);
        listView.setAdapter(adapter);

    }

    Handler handler = new Handler();
    Thread thread = new Thread()
    {
        @Override
        public void run()
        {
            super.run();
            try
            {
                URL url = new URL(appKey.getAppURL() + "areaBasedList?ServiceKey=" + appKey.getAppKey() + "&contentTypeId=25&areaCode=1" +
                        "&sigunguCode=&cat1=C01&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=O&numOfRows=12&pageNo=1");

                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
                {
                    int itemCount = readData(urlConnection.getInputStream());

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };


    public int readData(InputStream is)
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try
        {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(is);
            int datacount = parseDocument(document);
            return datacount;
        }
        catch (ParserConfigurationException e)
        {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int parseDocument(Document doc)
    {
        Element docEle = doc.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if((nodelist != null) && (nodelist.getLength() > 0))
        {
            for(int i = 0 ; i < nodelist.getLength() ; i++)
            {
                String newsItem = getTagData(nodelist, i);
                if( newsItem != null)
                {
                    datas.add(newsItem);
                    count++;
                }
            }
        }
        return count;
    }

    public String getTagData(NodeList nodelist, int index)
    {
        String newsItem = null;
        try
        {
            Element entry = (Element)nodelist.item(index);
            Element title = (Element)entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element)entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            String pubDateValue = null;
            if(title != null)
            {
                Log.d("땅아온니","136");
                Node firstChild = title.getFirstChild();
                if(firstChild != null) titleValue = firstChild.getNodeValue();
            }
            if(pubDate != null)
            {
                Log.d("땅아온니","142");
//                Node firstChild = pubDate.getFirstChild();
//                if(firstChild != null) pubDateValue = firstChild.getNodeValue();

                Node firstChild2 = pubDate.getFirstChild();
                if(firstChild2 != null) pubDateValue = firstChild2.getNodeValue();
                Log.d("땅아온니","148");
            }
            newsItem = titleValue;

        }
        catch (DOMException e)
        {
            e.printStackTrace();
        }
//        catch (ParcelFormatException e)
//        {
//            e.printStackTrace();
//        }
        return newsItem;
    }

    public void onClick(View v)
    {
        thread.start();
    }
}