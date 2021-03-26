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
import com.demo.theweather.network.pojo.Day;
import com.demo.theweather.presenters.DailyPresenter;
import com.demo.theweather.presenters.HourlyPresenter;

import java.util.List;


public class DailyFragment extends Fragment implements DailyContract.View {
    private SharedPreferences preferences;
    private DailyPresenter dailyPresenter = new DailyPresenter(this);
    private static final String TAG = "DailyFragment1";

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
    public void setDailyList(List<Day> listD) {

    }

    @Override
    public void onError() {

    }
}