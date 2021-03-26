package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyForecastList {

    @SerializedName("DailyForecasts")
    private List<DailyForecast> dailyForecasts = null;


    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

}