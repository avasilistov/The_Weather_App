package com.demo.theweather.model;

import android.util.Log;

import com.demo.theweather.contracts.DailyContract;
import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.WeatherService;
import com.demo.theweather.network.pojo.DailyForecast;
import com.demo.theweather.network.pojo.DailyForecastList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DailyRepository implements DailyContract.Repository {
    private final DailyRepository.Callback callback;
    private static final String TAG = "HourlyRepository1";
    private static final String IS_METRIC = "true";

    public interface Callback {
        void setDailyList(List<DailyForecast> listD);
    }


    public DailyRepository(DailyRepository.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void getDailyList(String locationKey) {

        WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
        Call<DailyForecastList> call = apiService.getDays(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<DailyForecastList>() {
            @Override
            public void onResponse(Call<DailyForecastList> call, Response<DailyForecastList> response) {
                Log.i(TAG, "onResponse: "+ response.body());
                if (response.isSuccessful() && response.body() != null ) {
                    List<DailyForecast> dayList = response.body().getDailyForecasts();
                    callback.setDailyList(dayList);
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
}
