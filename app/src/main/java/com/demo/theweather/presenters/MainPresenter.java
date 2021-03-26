package com.demo.theweather.presenters;


import android.util.Log;

import com.demo.theweather.contracts.MainContract;
import com.demo.theweather.model.MainRepository;

public class MainPresenter  implements MainContract.Presenter, MainRepository.Callback {
    private MainContract.View view;
    private MainContract.Repository repository;
    private String location;
    private static final String TAG = "MainPresenter1";

    public MainPresenter(MainContract.View view) {
        this.view = view;
        this.repository = new MainRepository(this);
    }

    @Override
    public void init() {

        location = view.getLocation();

        repository.getLocationKey(location);
    }

    @Override
    public void setLocationKey(String locationKey) {

        view.setLocationKey(locationKey);
    }


    @Override
    public void onDestroy() {

        view = null;
        repository = null;
    }


}
