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
    private final String PATH = "https://developer.accuweather.com/sites/default/files/";
    public static final String LOCATION_KEY = "location";
    public static final String COORDINATES_KEY = "coordinates";
    public static final String HOURLY_KEY = "hourly";
    public static final String DAILY_KEY = "daily";

    private LiveData<String> location;
    private LiveData<List<Hour>> listHoursWeather;
    private LiveData<List<DailyForecast>> listDailyWeather;
    private WeatherRepository repository;
    private final SavedStateHandle savedStateHandle;

    public void setCoordinates(String coordinates){
        savedStateHandle.set(COORDINATES_KEY, coordinates);
    }



    public WeatherViewModel(SavedStateHandle savedStateHandle) {
        this.savedStateHandle = savedStateHandle;
        this.repository = new WeatherRepository();
        LiveData<String> queryLocation = savedStateHandle.getLiveData(COORDINATES_KEY);
//        location = Transformations.switchMap(queryLocation, repository::getData);
    }

    public LiveData<String> getLocation() {
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



    public void getWeather(String coorditanes){

        repository.getData(coorditanes);

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

    private List<DailyForecast> addIconPathDay(List<DailyForecast> hourList){
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
}
