package com.demo.theweather.contracts;

import com.demo.theweather.network.pojo.Day;
import com.demo.theweather.network.pojo.Hour;

import java.util.List;

public interface DailyContract {
    public interface View {
        String getLocationKey();
        void setDailyList(List<Day> listD);
        void onError();
    }

    public interface Presenter {
        void init();
        void onDestroy();
    }

    interface Repository {
        void getDailyList(String locationKey);
    }
}
