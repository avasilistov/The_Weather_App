package com.demo.theweather.model;

import android.util.Log;

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
    private MutableLiveData<List<String>> locationData;
    private MutableLiveData<List<DailyForecast>> dailyData;
    private MutableLiveData<List<Hour>> hourlyData;


    public void getData(String location) {
        Call<City> call = apiService.searchCity(NetworkClient.API_KEY, location);
        call.enqueue(new retrofit2.Callback<City>() {

            @Override
            public void onResponse(Call<City> call, Response<City> response) {


                if (response.isSuccessful() && response.body() != null ) {
                    String locationKey = response.body().getKey();
                    String cityName = response.body().getLocalizedName();
                    Log.i(TAG, "onResponse: "+cityName);
                    locationData.setValue(Arrays.asList(locationKey, cityName));
                    getDailyList(locationData.getValue().get(0));
                    getHourlyList(locationData.getValue().get(0));
                }

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                t.printStackTrace();
            }

        });

    }


    public void getDailyList(String locationKey) {
        Call<DailyForecastList> call = apiService.getDays(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<DailyForecastList>() {
            @Override
            public void onResponse(Call<DailyForecastList> call, Response<DailyForecastList> response) {
                Log.i(TAG, "onResponse: "+ response.body());
                if (response.isSuccessful() && response.body() != null ) {
                    dailyData.setValue(response.body().getDailyForecasts());
                }

            }

            @Override
            public void onFailure(Call<DailyForecastList> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                Log.i(TAG, "onFailure: "+call.request().url());
                t.printStackTrace();
            }
        });

    }

    public void getHourlyList(String locationKey) {
        Call<List<Hour>> call = apiService.getHours(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<List<Hour>>() {
            @Override
            public void onResponse(Call<List<Hour>> call, Response<List<Hour>> response) {

                if (response.isSuccessful() && response.body() != null ) {
                   hourlyData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<Hour>> call, Throwable t) {

                t.printStackTrace();
            }
        });

    }
}
