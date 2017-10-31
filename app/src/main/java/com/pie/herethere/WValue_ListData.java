package com.pie.herethere;

public class WValue_ListData {
    String lat;
    String lon;
    String sky;
    String time;
    String temp;

    String getLat() {
        return lat;
    }
    String getLon() {
        return lon;
    }
    String getSky() {
        return sky;
    }
    String getTime() {
        return time;
    }
    String getTemp() {
        return temp;
    }

    WValue_ListData(String lat, String lon, String sky, String time, String temp) {
        this.lat = lat;
        this.lon = lon;
        this.sky = sky;
        this.time = time;
        this.temp = temp;
    }
}