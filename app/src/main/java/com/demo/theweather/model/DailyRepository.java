package com.demo.theweather.model;

import android.util.Log;

import com.demo.theweather.contracts.DailyContract;
import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.WeatherService;

import retrofit2.Call;
import retrofit2.Response;

public class DailyRepository implements DailyContract.Repository {
    private final DailyRepository.Callback callback;
    private static final String TAG = "MainRepository1";

    public interface Callback {
        void setDailyList(String locationKey);
    }


    public DailyRepository(DailyRepository.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void getLocationKey(String location) {
        Log.i(TAG, "getLocationKey: enter");
        WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
        Call<City> call = apiService.searchCity(NetworkClient.API_KEY, location);
        call.enqueue(new retrofit2.Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                Log.i(TAG, "onResponse: response.isSuccessful() "+response.isSuccessful());
                Log.i(TAG, "onResponse: response.body() "+response.body());

                if (response.isSuccessful() && response.body() != null ) {
                    String locationKey = response.body().getKey();
                    Log.i(TAG, "onResponse: "+locationKey);
                    Log.i(TAG, "onResponse: "+ response.body().getLocalizedName());
//                    callback.setLocationKey(locationKey);
                }

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
                t.printStackTrace();
            }
        });


    }
}
