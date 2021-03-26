package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class TemperatureH {
    @SerializedName("Value")
    private Double value;
    @SerializedName("Unit")
    private String unit;


    public Double getValue() {
        return value;
    }


    public String getUnit() {
        return unit;
    }

}
