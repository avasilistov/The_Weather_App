package com.demo.theweather.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.theweather.R;
import com.demo.theweather.adapters.DailyRecyclerAdapter;
import com.demo.theweather.mvvm.WeatherViewModel;
import com.demo.theweather.network.pojo.DailyForecast;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


public class DailyFragment extends Fragment {
    private SharedPreferences preferences;
    private static final String TAG = "DailyFragment1";
    private RecyclerView recyclerView;
    private TextView txtDailyFragmCenter, txtCityName;
    private WeatherViewModel weatherViewModel;

    public DailyFragment() {
    }


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
        recyclerView = view.findViewById(R.id.recycler_view_daily);
        txtDailyFragmCenter = view.findViewById(R.id.text_view_daily_center_temp);
        txtCityName = view.findViewById(R.id.text_view_daily_city_name);
        weatherViewModel = new ViewModelProvider(this,
                new SavedStateViewModelFactory(requireActivity().getApplication(), this))
                .get(WeatherViewModel.class);


        weatherViewModel.getListDailyWeather().observe(getViewLifecycleOwner(), dailyForecasts -> {
            if (dailyForecasts != null) {
                DailyRecyclerAdapter adapter = new DailyRecyclerAdapter(dailyForecasts, getContext());
                txtDailyFragmCenter.setText(dailyForecasts.get(0).getTemperature().getMaximum().getValue());
                recyclerView.setAdapter(adapter);
            }
        });


        weatherViewModel.getLocation().observe(getViewLifecycleOwner(), cityName ->
        {
            if (cityName != null) txtCityName.setText(cityName);
        });


    }


    public void onError() {
        Snackbar.make(getActivity().findViewById(R.id.daily_fragment), R.string.cant_determine_location, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, view -> {
                }).show();
    }
}