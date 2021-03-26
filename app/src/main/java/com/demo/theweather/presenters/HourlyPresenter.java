package com.demo.theweather.presenters;


import android.util.Log;

import com.demo.theweather.contracts.HourlyContract;
import com.demo.theweather.model.HourlyRepository;
import com.demo.theweather.network.pojo.Hour;

import java.util.List;

public class HourlyPresenter implements HourlyContract.Presenter, HourlyRepository.Callback {
    private HourlyContract.View view;
    private HourlyContract.Repository repository;
    private static final String TAG = "HourlyPresenter1";

    public HourlyPresenter(HourlyContract.View view) {
        this.view = view;
        this.repository = new HourlyRepository(this);
    }

    @Override
    public void init() {
        String key = view.getLocationKey();
        Log.i(TAG, "init: "+key);
        if (key!=null) {
            repository.getHourlyList(key);
        } else {
            view.onError();
        }
    }

    @Override
    public void onDestroy() {
        view = null;
        repository = null;
    }

    @Override
    public void setHourlyList(List<Hour> listH) {
        view.setHourlyList(listH);
    }
}
