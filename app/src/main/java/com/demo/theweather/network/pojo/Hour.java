package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class Hour {
    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("WeatherIcon")
    private Integer weatherIcon;

    @SerializedName("IconPhrase")
    private String iconPhrase;

    @SerializedName("HasPrecipitation")
    private Boolean hasPrecipitation;

    @SerializedName("IsDaylight")
    private Boolean isDaylight;

    @SerializedName("Temperature")
    private TemperatureH temperature;

    @SerializedName("PrecipitationProbability")
    private String precipitationProbability;

    public String getDateTime() {
        return dateTime;
    }


    public Integer getWeatherIcon() {
        return weatherIcon;
    }


    public String getIconPhrase() {
        return iconPhrase;
    }


    public Boolean getHasPrecipitation() {
        return hasPrecipitation;
    }


    public Boolean getIsDaylight() {
        return isDaylight;
    }


    public TemperatureH getTemperature() {
        return temperature;
    }

    public String precipitationProbability() {
        return precipitationProbability;
    }
}
