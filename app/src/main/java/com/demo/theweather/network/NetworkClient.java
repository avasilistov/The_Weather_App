package com.demo.theweather.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    public static final String BASE_URL = "http://dataservice.accuweather.com";
    public static final String PATH = "https://developer.accuweather.com/sites/default/files/";
    //kv
//   public static final String API_KEY = "IhkUKvJq5bycqpPWUX5ZikEGYURtaKZ0";
    //ov
    public static final String API_KEY = "e4NM7wa9MixLJeQFYnmQd7S5uPfPMU4Y";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(new OkHttpClient.Builder().addInterceptor(new WeatherInterceptor())
                            .build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;

    }
}
