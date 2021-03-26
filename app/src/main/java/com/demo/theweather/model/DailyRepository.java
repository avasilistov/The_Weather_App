package com.demo.theweather.model;

import android.util.Log;

import com.demo.theweather.contracts.DailyContract;
import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.WeatherService;
import com.demo.theweather.network.pojo.Day;
import com.demo.theweather.network.pojo.Hour;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DailyRepository implements DailyContract.Repository {
    private final DailyRepository.Callback callback;
    private static final String TAG = "HourlyRepository1";
    private static final String IS_METRIC = "true";

    public interface Callback {
        void setDailyList(List<Day> listD);
    }


    public DailyRepository(DailyRepository.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void getDailyList(String locationKey) {

        WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
        Call<List<Day>> call = apiService.getDays(locationKey, NetworkClient.API_KEY);

        call.enqueue(new retrofit2.Callback<List<Day>>() {
            @Override
            public void onResponse(Call<List<Day>> call, Response<List<Day>> response) {

                if (response.isSuccessful() && response.body() != null ) {
                    List<Day> dayList = response.body();
                    callback.setDailyList(dayList);
                }

            }

            @Override
            public void onFailure(Call<List<Day>> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }
}
