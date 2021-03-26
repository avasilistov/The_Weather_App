package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class TemperatureH {
    @SerializedName("Value")
    private String value;
    @SerializedName("Unit")
    private String unit;


    public String getValue() {
        return value;
    }


    public String getUnit() {
        return unit;
    }

}
