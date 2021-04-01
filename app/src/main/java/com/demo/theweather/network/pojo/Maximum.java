package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Maximum {
    @SerializedName("Value")
    private String value;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("UnitType")
    private Integer unitType;

    public String getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitType() {
        return unitType;
    }

}