package com.demo.theweather.contracts;

import com.demo.theweather.network.pojo.Hour;

import java.util.List;

public interface HourlyContract {
    public interface View {
        String getLocationKey();
        void setHourlyList(List<Hour> listH);
        void onError();
    }

    public interface Presenter {
        void init();
        void onDestroy();
    }

    interface Repository {
        void getHourlyList(String locationKey);
    }
}
