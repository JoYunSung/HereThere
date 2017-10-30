package com.pie.herethere.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("weather")
    @Expose
    public Weather weather;
    @SerializedName("common")
    @Expose
    public Common common;
    @SerializedName("result")
    @Expose
    public Result result;

}
