package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HoursList {
    public List<Hour> getHourList() {
        return hourList;
    }

    @SerializedName("hours")
    private List<Hour> hourList = new ArrayList<>();
}
