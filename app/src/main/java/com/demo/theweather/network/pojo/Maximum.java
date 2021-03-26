package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Maximum {
    @SerializedName("Value")
    private Double value;
    @SerializedName("Unit")
    private String unit;
    @SerializedName("UnitType")
    private Integer unitType;

    public Double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public Integer getUnitType() {
        return unitType;
    }

}