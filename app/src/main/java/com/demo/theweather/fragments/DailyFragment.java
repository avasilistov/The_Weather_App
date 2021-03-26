package com.demo.theweather.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.theweather.R;
import com.demo.theweather.contracts.DailyContract;
import com.demo.theweather.network.pojo.DailyForecast;
import com.demo.theweather.network.pojo.Hour;
import com.demo.theweather.presenters.DailyPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class DailyFragment extends Fragment implements DailyContract.View {
    private SharedPreferences preferences;
    private DailyPresenter dailyPresenter = new DailyPresenter(this);
    private static final String TAG = "DailyFragment1";
    private final String PATH = "https://developer.accuweather.com/sites/default/files/";

    public DailyFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        dailyPresenter.init();
    }

    @Override
    public String getLocationKey() {
        String locationKey = preferences.getString("locationKey", null);
        if (locationKey != null) return locationKey;
        return null;
    }

    @Override
    public void setDailyList(List<DailyForecast> listD) {
        listD = addIconPath(listD);

    }

    private List<DailyForecast> addIconPath(List<DailyForecast> hourList){
        ArrayList<DailyForecast> tempList = new ArrayList<>();
        String tempPathDay = "";
        String tempPathNight = "";
        for (DailyForecast day : hourList) {
            tempPathDay = day.getDay().getIcon();
            tempPathNight = day.getNight().getIcon();
            if (Integer.valueOf(tempPathDay) < 10) {

                tempPathDay = PATH + "0" + tempPathDay + "-s.png";
                tempPathNight = PATH + "0" + tempPathNight + "-s.png";
            } else {
                tempPathDay = PATH + tempPathDay + "-s.png";
                tempPathNight = PATH + tempPathNight + "-s.png";
            }

            day.getDay().setIcon(tempPathDay);
            day.getNight().setIcon(tempPathNight);
            tempList.add(day);
        }
        return tempList;
    }

    @Override
    public void onError() {
        Snackbar.make(getActivity().findViewById(R.id.daily_fragment), R.string.cant_determine_location, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, view -> {
                }).show();
    }
}