package com.demo.theweather.model;

import android.util.Log;

import com.demo.theweather.contracts.MainContract;
import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.WeatherService;

import retrofit2.Call;
import retrofit2.Response;

public class MainRepository implements MainContract.Repository {
    private final Callback callback;
    private static final String TAG = "MainRepository1";

    public interface Callback {
        void setLocationKey(String locationKey);
    }


    public MainRepository(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void getLocationKey(String location) {

        WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
        Call<City> call = apiService.searchCity(NetworkClient.API_KEY, location);
        call.enqueue(new retrofit2.Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {


                if (response.isSuccessful() && response.body() != null ) {
                    String locationKey = response.body().getKey();

                    callback.setLocationKey(locationKey);
                }

            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }
}
