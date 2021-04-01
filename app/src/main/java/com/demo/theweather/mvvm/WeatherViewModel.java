package com.demo.theweather.mvvm;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.demo.theweather.model.WeatherRepository;
import com.demo.theweather.network.pojo.DailyForecast;
import com.demo.theweather.network.pojo.Hour;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel extends ViewModel {
    private static final String TAG = "WeatherViewModel";

    public static final String LOCATION_KEY = "location";
    public static final String HOURLY_KEY = "hourly";
    public static final String DAILY_KEY = "daily";

    private LiveData<List<String>> location;
    private LiveData<List<Hour>> listHoursWeather;
    private LiveData<List<DailyForecast>> listDailyWeather;
    private WeatherRepository repository;
    private final SavedStateHandle savedStateHandle;


    public void setQueryLocation(String location){
        savedStateHandle.set(LOCATION_KEY, location);
    }

    public void setQueryWeather(String locationKey){
        Log.i(TAG, "setQueryWeather: "+locationKey);
        savedStateHandle.set(DAILY_KEY, locationKey);
        savedStateHandle.set(HOURLY_KEY, locationKey);
    }






    public WeatherViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.repository = new WeatherRepository();
        LiveData<String> queryLocation = savedStateHandle.getLiveData(LOCATION_KEY);
        LiveData<String> queryDailyWeather = savedStateHandle.getLiveData(DAILY_KEY);
        LiveData<String> queryHourlyWeather = savedStateHandle.getLiveData(HOURLY_KEY);
        location = Transformations.switchMap(queryLocation, repository::getData);
        listDailyWeather = Transformations.switchMap(queryDailyWeather, repository::getDailyList);
        listHoursWeather = Transformations.switchMap(queryHourlyWeather, repository::getHourlyList);


    }



    public LiveData<List<String>> getLocation() {
        if (location == null){
            location = new MutableLiveData<>();
        }
        return location;
    }

    public LiveData<List<Hour>> getListHoursWeather() {
        if (listHoursWeather == null){
            listHoursWeather = new MutableLiveData<>();
        }
        return listHoursWeather;
    }

    public LiveData<List<DailyForecast>> getListDailyWeather() {
        if (listDailyWeather == null){
            listDailyWeather = new MutableLiveData<>();
        }
        return listDailyWeather;
    }






}
