package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class DailyForecast {
    @SerializedName("Date")
    private String date;

    @SerializedName("Temperature")
    private TemperatureD.Temperature temperature;

    @SerializedName("Day")
    private Day day;

    @SerializedName("Night")
    private Night night;


    public String getDate() {
        return date;
    }

    public TemperatureD.Temperature getTemperature() {
        return temperature;
    }

    public Day getDay() {
        return day;
    }

    public Night getNight() {
        return night;
    }

}