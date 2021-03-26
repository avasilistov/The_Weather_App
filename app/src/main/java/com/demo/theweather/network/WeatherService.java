package com.demo.theweather.network;

import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.pojo.Day;
import com.demo.theweather.network.pojo.Hour;
import com.demo.theweather.network.pojo.HoursList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;







public interface WeatherService {
    @GET("/locations/v1/cities/geoposition/search")
    Call<City> searchCity(@Query("apikey") String apikey, @Query("q") String coordinates);

    @GET("/forecasts/v1/hourly/12hour/{locationKey}")
    Call<List<Hour>> getHours(@Path("locationKey") String locationKey, @Query("apikey") String apikey, @Query("metric") String metric);

    @GET("forecasts/v1/daily/5day/{locationKey}")
    Call<List<Day>> getDays(@Path("locationKey") String locationKey, @Query("apikey") String apikey);
}

