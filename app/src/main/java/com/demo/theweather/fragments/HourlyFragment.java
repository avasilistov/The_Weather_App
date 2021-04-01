package com.demo.theweather.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.theweather.R;
import com.demo.theweather.mvvm.WeatherViewModel;
import com.demo.theweather.network.pojo.Hour;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.demo.theweather.network.NetworkClient.PATH;


public class HourlyFragment extends Fragment {
    private WeatherViewModel weatherViewModel;
    private SharedPreferences preferences;

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
        weatherViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(WeatherViewModel.class);

        weatherViewModel.getListHoursWeather().observe(getViewLifecycleOwner(), hourList -> {
            if (hourList!=null){

                //TODO
            }

        });
        weatherViewModel.getLocation().observe(getViewLifecycleOwner(), cityName ->
        {
//            if (cityName != null) txtCityName.setText(cityName);
        });
    }

    private List<Hour> addIconPathHour(List<Hour> hourList){
        ArrayList<Hour> tempList = new ArrayList<>();
        String tempPath = "";
        for (Hour hour : hourList) {
            tempPath = hour.getWeatherIcon();
            if (Integer.valueOf(tempPath) < 10) {

                tempPath = PATH + "0" + tempPath + "-s.png";
            } else {
                tempPath = PATH + tempPath + "-s.png";
            }
            Log.i(TAG, "setWeatherIcon: " + tempPath);
            hour.setWeatherIcon(tempPath);
            tempList.add(hour);
        }
        return tempList;
    }

    public void onError() {
        Snackbar.make(getActivity().findViewById(R.id.hourly_fragment), R.string.cant_determine_location, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, view -> {
                }).show();
    }
}