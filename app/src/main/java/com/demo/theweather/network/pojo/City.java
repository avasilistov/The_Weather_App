package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("Key")
    private String key;
    @SerializedName("LocalizedName")
    private String localizedName;

    public String getLocalizedName() {
        return localizedName;
    }

    public String getKey() {
        return key;
    }


}
