package com.pie.herethere;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    public static final String WIFE_STATE = "WIFE";
    public static final String MOBILE_STATE = "MOBILE";
    public static final String NONE_STATE = "NONE";

    boolean isBackOk = false;
    public static final String CONNECTION_CONFIRM_CLIENT_URL = "http://clients3.google.com/generate_204";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        switch (getWhatKindOfNetwork(getApplicationContext())) {
            case WIFE_STATE:
                if (isOnline())
                    Move();
                break;
            case MOBILE_STATE:
                Toast.makeText(getApplicationContext(), Data, Toast.LENGTH_SHORT).show();
                if (isOnline())
                    Move();
                break;
            case NONE_STATE:
                Toast.makeText(getApplicationContext(), Error, Toast.LENGTH_SHORT).show();
                break;
            default:
                finish();
        }
    }

    public String Data = "모바일 데이터 연결중입니다. 별도의 데이터 통화료가 발생할 수 있습니다.";
    public String Error = "인터넷에 연결되어 있지 않습니다. Wifi 또는 모바일 데이터가 켜져 있는지 확인해 주세요.";

    public static String getWhatKindOfNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFE_STATE;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return MOBILE_STATE;
            }
        }
        return NONE_STATE;
    }

    private static class CheckConnect extends Thread {
        private boolean success;
        private String host;

        public CheckConnect(String host) {
            this.host = host;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection) new URL(host).openConnection();
                conn.setRequestProperty("User-Agent", "Android");
                conn.setConnectTimeout(1000);
                conn.connect();
                int responseCode = conn.getResponseCode();
                if (responseCode == 204) success = true;
                else success = false;
            } catch (Exception e) {
                e.printStackTrace();
                success = false;
            }
            if (conn != null) {
                conn.disconnect();
            }
        }

        public boolean isSuccess() {
            return success;
        }
    }

    public void Move() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isBackOk) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
            }
        }, 2000);
    }

    public static boolean isOnline() {
        CheckConnect cc = new CheckConnect(CONNECTION_CONFIRM_CLIENT_URL);
        cc.start();
        try {
            cc.join();
            return cc.isSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}