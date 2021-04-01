package com.demo.theweather.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.WeatherService;
import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.pojo.DailyForecast;
import com.demo.theweather.network.pojo.DailyForecastList;
import com.demo.theweather.network.pojo.Hour;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class WeatherRepository {
    private static final String TAG = "WeatherRepository";
    private static final String IS_METRIC = "true";
    private WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
    private MutableLiveData<List<String>> locationData = new MutableLiveData<>();
    private MutableLiveData<List<DailyForecast>> dailyData = new MutableLiveData<>();
    private MutableLiveData<List<Hour>> hourlyData = new MutableLiveData<>();


    public MutableLiveData<List<String>> getLocationData() {
        return locationData;
    }

    public MutableLiveData<List<DailyForecast>> getDailyData(List<String> list) {
        return dailyData;
    }

    public MutableLiveData<List<Hour>> getHourlyData() {
        return hourlyData;
    }

    public LiveData<List<String>> getData(String location) {
        Call<City> call = apiService.searchCity(NetworkClient.API_KEY, location);
        call.enqueue(new retrofit2.Callback<City>() {

            @Override
            public void onResponse(Call<City> call, Response<City> response) {


                if (response.isSuccessful() && response.body() != null ) {
                    String locationKey = response.body().getKey();
                    String cityName = response.body().getLocalizedName();
                    Log.i(TAG, "onResponse: "+cityName);
                    locationData.postValue(Arrays.asList(locationKey, cityName));

                }

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                t.printStackTrace();
            }

        });

        return locationData;
    }


    public LiveData<List<DailyForecast>> getDailyList(String locationKey) {
        Call<DailyForecastList> call = apiService.getDays(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<DailyForecastList>() {
            @Override
            public void onResponse(Call<DailyForecastList> call, Response<DailyForecastList> response) {
                Log.i(TAG, "onResponse: "+ response.body());
                if (response.isSuccessful() && response.body() != null ) {
                    dailyData.postValue(response.body().getDailyForecasts());
                }

            }

            @Override
            public void onFailure(Call<DailyForecastList> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                Log.i(TAG, "onFailure: "+call.request().url());
                t.printStackTrace();
            }
        });
        return dailyData;

    }

    public LiveData<List<Hour>> getHourlyList(String locationKey) {
        Call<List<Hour>> call = apiService.getHours(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<List<Hour>>() {
            @Override
            public void onResponse(Call<List<Hour>> call, Response<List<Hour>> response) {

                if (response.isSuccessful() && response.body() != null ) {
                   hourlyData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Hour>> call, Throwable t) {

                t.printStackTrace();
            }
        });
        return hourlyData;

    }
}
