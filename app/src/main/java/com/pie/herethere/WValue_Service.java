package com.pie.herethere;

import com.pie.herethere.App.AppKey;
import com.pie.herethere.Model.ForecastInfo;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface WValue_Service {

    AppKey app = new AppKey();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://apis.skplanetx.com/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @Headers("appKey: " + AppKey.WeatherAppKey)
    @GET("forecast/3days?")
    Call<ForecastInfo>getForecase3Days(@Query("version")int version,
                                       @Query("lat") String lat, @Query("lon")String lon);
}