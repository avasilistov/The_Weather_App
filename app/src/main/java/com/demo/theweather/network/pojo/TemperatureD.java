package com.demo.theweather.network.pojo;

import com.google.gson.annotations.SerializedName;

public class TemperatureD {

    public class Temperature {

        @SerializedName("Minimum")
        private Minimum minimum;

        @SerializedName("Maximum")
        private Maximum maximum;

        public Minimum getMinimum() {
            return minimum;
        }

        public Maximum getMaximum() {
            return maximum;
        }

    }
}
