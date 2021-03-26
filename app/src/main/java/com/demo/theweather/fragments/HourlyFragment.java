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
import com.demo.theweather.contracts.HourlyContract;
import com.demo.theweather.network.pojo.Hour;
import com.demo.theweather.presenters.HourlyPresenter;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class HourlyFragment extends Fragment implements HourlyContract.View {
    private SharedPreferences preferences;
    private HourlyPresenter hourlyPresenter = new HourlyPresenter(this);
    private static final String TAG = "HourlyFragment1";

    public HourlyFragment() {}



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_hourly, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        preferences = getContext().getSharedPreferences("location", Context.MODE_PRIVATE);
        hourlyPresenter.init();

    }

    @Override
    public String getLocationKey() {
        String locationKey = preferences.getString("locationKey", null);
        Log.i(TAG, "getLocationKey: "+locationKey);
        if (locationKey != null) return locationKey;
        return null;
    }

    @Override
    public void setHourlyList(List<Hour> listH) {
        for (Hour hour : listH ){
            
        }
    }

    @Override
    public void onError() {
        Snackbar.make(getActivity().findViewById(R.id.daily_fragment), R.string.cant_determine_location, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, view -> {
                }).show();
    }
}