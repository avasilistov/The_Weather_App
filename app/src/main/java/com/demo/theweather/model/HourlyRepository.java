package com.demo.theweather.model;

import android.util.Log;

import com.demo.theweather.R;
import com.demo.theweather.contracts.HourlyContract;
import com.demo.theweather.network.NetworkClient;
import com.demo.theweather.network.WeatherService;
import com.demo.theweather.network.pojo.City;
import com.demo.theweather.network.pojo.Hour;
import com.demo.theweather.network.pojo.HoursList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HourlyRepository implements HourlyContract.Repository {
    private final HourlyRepository.Callback callback;
    private static final String TAG = "HourlyRepository1";
    private static final String IS_METRIC = "true";

    public interface Callback {
        void setHourlyList(List<Hour> listH);
    }


    public HourlyRepository(HourlyRepository.Callback callback) {
        this.callback = callback;
    }

    @Override
    public void getHourlyList(String locationKey) {

        WeatherService apiService = NetworkClient.getClient().create(WeatherService.class);
        Call<List<Hour>> call = apiService.getHours(locationKey, NetworkClient.API_KEY, IS_METRIC);

        call.enqueue(new retrofit2.Callback<List<Hour>>() {
            @Override
            public void onResponse(Call<List<Hour>> call, Response<List<Hour>> response) {

                if (response.isSuccessful() && response.body() != null ) {
                    List<Hour> hourList = response.body();
                    callback.setHourlyList(hourList);
                }

            }

            @Override
            public void onFailure(Call<List<Hour>> call, Throwable t) {

                t.printStackTrace();
            }
        });


    }
}
