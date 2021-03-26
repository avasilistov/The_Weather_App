package com.demo.theweather.contracts;

public interface DailyContract {
    public interface View {
        String getLocation();
        void setLocationKey(String locationKey);
    }

    public interface Presenter {
        void init();
        void onDestroy();
    }

    interface Repository {
        void getLocationKey(String location);
    }
}
