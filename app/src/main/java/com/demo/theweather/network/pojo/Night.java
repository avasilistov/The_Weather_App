package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Night {
    @SerializedName("Icon")
    private String icon;

    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("HasPrecipitation")
    private Boolean hasPrecipitation;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconPhrase() {
        return iconPhrase;
    }


    public Boolean getHasPrecipitation() {
        return hasPrecipitation;
    }


}
