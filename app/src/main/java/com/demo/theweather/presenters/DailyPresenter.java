package com.demo.theweather.presenters;

import android.util.Log;

import com.demo.theweather.contracts.DailyContract;
import com.demo.theweather.contracts.HourlyContract;
import com.demo.theweather.contracts.MainContract;
import com.demo.theweather.model.DailyRepository;
import com.demo.theweather.model.HourlyRepository;
import com.demo.theweather.model.MainRepository;
import com.demo.theweather.network.pojo.Day;
import com.demo.theweather.network.pojo.Hour;

import java.util.List;

public class DailyPresenter implements DailyContract.Presenter, DailyRepository.Callback {
    private DailyContract.View view;
    private DailyContract.Repository repository;
    private static final String TAG = "DailyPresenter1";

    public DailyPresenter(DailyContract.View view) {
        this.view = view;
        this.repository = new DailyRepository(this);
    }

    @Override
    public void init() {
        String key = view.getLocationKey();

        if (key != null) {
            repository.getDailyList(key);
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
    public void setDailyList(List<Day> listD) {
        view.setDailyList(listD);
    }
}